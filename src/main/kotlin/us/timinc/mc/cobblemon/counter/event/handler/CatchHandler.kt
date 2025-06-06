package us.timinc.mc.cobblemon.counter.event.handler

import com.cobblemon.mod.common.api.events.pokemon.PokemonCapturedEvent
import us.timinc.mc.cobblemon.counter.extensions.record
import us.timinc.mc.cobblemon.counter.registry.CounterTypes

object CatchHandler {
    fun handle(pokemonCapturedEvent: PokemonCapturedEvent) {
        val player = pokemonCapturedEvent.player
        val pokemon = pokemonCapturedEvent.pokemon
        player.record(pokemon, CounterTypes.CAPTURE)
    }
}