package us.timinc.mc.cobblemon.counter.extensions

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.CounterMod
import us.timinc.mc.cobblemon.counter.api.CounterManager
import us.timinc.mc.cobblemon.counter.api.CounterType
import us.timinc.mc.cobblemon.counter.storage.PlayerInstancedDataStores

fun ServerPlayer.getCounterManager(): CounterManager {
    return Cobblemon.playerDataManager.get(this, PlayerInstancedDataStores.COUNTER) as CounterManager
}

fun ServerPlayer.record(pokemon: Pokemon, counterType: CounterType) {
    CounterMod.debug("Player ${name.string}|$uuid ${counterType.type}'d a ${pokemon.species.resourceIdentifier}|${pokemon.form.name}")
    val counterManager = getCounterManager()
    counterManager.record(pokemon, counterType)
}