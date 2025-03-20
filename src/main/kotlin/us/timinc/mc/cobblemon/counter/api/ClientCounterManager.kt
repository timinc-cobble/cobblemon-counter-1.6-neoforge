package us.timinc.mc.cobblemon.counter.api

import com.cobblemon.mod.common.api.storage.player.client.ClientInstancedPlayerData
import com.cobblemon.mod.common.net.messages.client.SetClientPlayerDataPacket
import com.cobblemon.mod.common.util.readString
import com.cobblemon.mod.common.util.writeString
import net.minecraft.client.Minecraft
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import us.timinc.mc.cobblemon.counter.CounterModClient
import us.timinc.mc.cobblemon.counter.storage.PlayerInstancedDataStores

class ClientCounterManager(
    override val counters: MutableMap<CounterType, Counter>,
) : AbstractCounterManager(), ClientInstancedPlayerData {
    override fun encode(buf: RegistryFriendlyByteBuf) {
        buf.writeMap(
            counters,
            { _, key -> buf.writeString(key.type) },
            { _, value -> value.encode(buf) }
        )
    }

    companion object {
        fun decode(buf: RegistryFriendlyByteBuf): SetClientPlayerDataPacket {
            val map = buf.readMap(
                { buf.readString().let { type -> CounterType.entries.find { it.type == type }!! } },
                { Counter().also { it.decode(buf) } }
            )
            return SetClientPlayerDataPacket(PlayerInstancedDataStores.COUNTER, ClientCounterManager(map))
        }

        fun runAction(data: ClientInstancedPlayerData) {
            if (data !is ClientCounterManager) return
            CounterModClient.clientCounterData = data
        }

        fun runActionIncremental(data: ClientInstancedPlayerData) {
            if (data !is ClientCounterManager) return

            val clientData = CounterModClient.clientCounterData
            for ((counterType, counter) in data.counters.entries) {
                val changedStreak = counter.streak.species.toString() != "minecraft:empty"
                val targetClientCounter = clientData.counters[counterType]
                    ?: throw Error("Unregistered counter type sent by server ${counterType.type}")
                for ((speciesId, speciesRecord) in counter.count) {
                    val clientSpeciesRecord = targetClientCounter.count.getOrPut(speciesId, ::mutableMapOf)
                    for ((formName, count) in speciesRecord) {
                        if (CounterModClient.config.broadcast.contains(counterType.type)) {
                            val player = Minecraft.getInstance().player ?: return
                            player.sendSystemMessage(
                                Component.translatable(
                                    "cobbled_counter.broadcast.${counterType.type}",
                                    Component.translatable("cobblemon.species.${speciesId.path}.name"),
                                    if (formName == "Normal") "" else Component.translatable(
                                        "cobbled_counter.item.counter.tooltip.form",
                                        formName
                                    ),
                                    count,
                                    if (changedStreak) {
                                        Component.translatable(
                                            "cobbled_counter.broadcast.details.streak",
                                            counter.streak.count
                                        )
                                    } else ""
                                )
                            )
                        }

                        clientSpeciesRecord[formName] = count
                    }
                }
                if (changedStreak) targetClientCounter.streak = counter.streak
            }
        }
    }
}