package us.timinc.mc.cobblemon.counter.event.handler

import com.cobblemon.mod.common.api.events.pokemon.FossilRevivedEvent
import us.timinc.mc.cobblemon.counter.extensions.record
import us.timinc.mc.cobblemon.counter.registry.CounterTypes

object FossilRevivedHandler {
    fun handle(fossilRevivedEvent: FossilRevivedEvent) {
        val pokemon = fossilRevivedEvent.pokemon
        val player = fossilRevivedEvent.player ?: return
        player.record(pokemon, CounterTypes.RESURRECTION)
    }
}
