package us.timinc.mc.cobblemon.counter.event.handler

import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent
import com.cobblemon.mod.common.util.getPlayer
import us.timinc.mc.cobblemon.counter.api.CounterType
import us.timinc.mc.cobblemon.counter.extensions.record
import java.util.*

object BattleFaintedHandler {
    fun handle(event: BattleFaintedEvent) {
        val pokemon = event.killed.effectedPokemon
        if (!event.battle.isPvW || !pokemon.isWild()) {
            return
        }
        val players = event.battle.playerUUIDs.mapNotNull(UUID::getPlayer)

        players.forEach { player ->
            player.record(pokemon, CounterType.KO)
        }
    }
}