package us.timinc.mc.cobblemon.counter.api

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.resources.ResourceLocation
import us.timinc.mc.cobblemon.counter.CounterMod.modResource

class Streak(
    var species: ResourceLocation = EMPTY_SPECIES,
    var form: String = "normal",
    var count: Int = 0,
) {
    companion object {
        val CODEC: Codec<Streak> = RecordCodecBuilder.create { instance ->
            instance.group(
                ResourceLocation.CODEC.fieldOf("species").forGetter { it.species },
                PrimitiveCodec.STRING.fieldOf("form").forGetter { it.form },
                PrimitiveCodec.INT.fieldOf("count").forGetter { it.count }
            ).apply(instance) { species, form, count ->
                Streak(species, form, count)
            }
        }
        val IGNORED_SPECIES = modResource("ignored")
        val EMPTY_SPECIES = modResource("empty")
    }

    fun clone(): Streak {
        return Streak(species, form, count)
    }

    fun wouldBreak(speciesId: ResourceLocation, formName: String): Boolean {
        return speciesId != species || form != formName
    }

    fun changed(): Boolean = species != IGNORED_SPECIES
}