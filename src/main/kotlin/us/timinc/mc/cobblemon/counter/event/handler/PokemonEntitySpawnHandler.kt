package us.timinc.mc.cobblemon.counter.event.handler

import com.cobblemon.mod.common.api.events.entity.SpawnEvent
import com.cobblemon.mod.common.api.spawning.context.FishingSpawningContext
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.CounterType
import us.timinc.mc.cobblemon.counter.extensions.record

object PokemonEntitySpawnHandler {
    fun handle(spawnEvent: SpawnEvent<PokemonEntity>) {
        val ctx = spawnEvent.ctx
        if (ctx !is FishingSpawningContext) return

        val player = ctx.cause.entity
        if (player !is ServerPlayer) return

        val pokemon = spawnEvent.entity.pokemon
        player.record(pokemon, CounterType.FISH)
    }

}
