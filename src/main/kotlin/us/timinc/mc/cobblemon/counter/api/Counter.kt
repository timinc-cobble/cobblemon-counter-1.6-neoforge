package us.timinc.mc.cobblemon.counter.api

import com.cobblemon.mod.common.util.asIdentifierDefaultingNamespace
import com.cobblemon.mod.common.util.readString
import com.cobblemon.mod.common.util.writeString
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import us.timinc.mc.cobblemon.counter.CounterMod

class Counter(
    val count: MutableMap<ResourceLocation, MutableMap<String, Int>> = mutableMapOf(),
    var streak: Streak = Streak(),
) {
    companion object {
        val CODEC: Codec<Counter> = RecordCodecBuilder.create { instance ->
            instance.group(Codec.unboundedMap(
                ResourceLocation.CODEC, Codec.unboundedMap(PrimitiveCodec.STRING, PrimitiveCodec.INT)
            ).fieldOf("count").forGetter { it.count }, Streak.CODEC.fieldOf("streak").forGetter { it.streak })
                .apply(instance) { count, streak ->
                    val clonedCount: MutableMap<ResourceLocation, MutableMap<String, Int>> = mutableMapOf()
                    count.forEach { (speciesId, speciesRecord) ->
                        clonedCount[speciesId] = speciesRecord.toMutableMap()
                    }
                    Counter(clonedCount, streak)
                }
        }
    }

    fun clone(): Counter {
        return Counter(
            count.toMutableMap(), streak.clone()
        )
    }

    fun encode(buf: RegistryFriendlyByteBuf) {
        buf.writeString(streak.species.toString())
        buf.writeString(streak.form)
        buf.writeInt(streak.count)

        buf.writeInt(count.size)
        for ((resourceLocation, speciesRecord) in count) {
            buf.writeString(resourceLocation.toString())
            buf.writeInt(speciesRecord.size)
            for ((formName, count) in speciesRecord) {
                buf.writeString(formName)
                buf.writeInt(count)
            }
        }
    }

    fun decode(buf: RegistryFriendlyByteBuf) {
        streak.species = buf.readString().asIdentifierDefaultingNamespace(CounterMod.MOD_ID)
        streak.form = buf.readString()
        streak.count = buf.readInt()

        count.clear()
        val countSize = buf.readInt()
        for (i in 0 until countSize) {
            val speciesId = buf.readString().asIdentifierDefaultingNamespace(CounterMod.MOD_ID)
            val speciesRecord: MutableMap<String, Int> = mutableMapOf()
            val speciesRecordSize = buf.readInt()
            for (j in 0 until speciesRecordSize) {
                val formName = buf.readString()
                val count = buf.readInt()
                speciesRecord[formName] = count
            }
            count[speciesId] = speciesRecord
        }
    }
}