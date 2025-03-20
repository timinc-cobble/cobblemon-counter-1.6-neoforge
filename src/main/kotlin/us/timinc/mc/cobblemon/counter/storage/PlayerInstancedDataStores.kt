package us.timinc.mc.cobblemon.counter.storage

import com.cobblemon.mod.common.api.storage.player.PlayerInstancedDataStoreType
import com.cobblemon.mod.common.api.storage.player.PlayerInstancedDataStoreTypes
import us.timinc.mc.cobblemon.counter.CounterMod
import us.timinc.mc.cobblemon.counter.api.ClientCounterManager

object PlayerInstancedDataStores {
    val COUNTER = PlayerInstancedDataStoreTypes.register(
        PlayerInstancedDataStoreType(
            CounterMod.modResource("counter"),
            ClientCounterManager::decode,
            ClientCounterManager::runAction,
            ClientCounterManager::runActionIncremental
        )
    )
}