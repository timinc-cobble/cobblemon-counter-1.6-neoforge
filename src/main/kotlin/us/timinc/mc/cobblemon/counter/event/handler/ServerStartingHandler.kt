package us.timinc.mc.cobblemon.counter.event.handler

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.storage.player.factory.CachedPlayerDataStoreFactory
import net.neoforged.neoforge.event.server.ServerStartedEvent
import us.timinc.mc.cobblemon.counter.storage.CounterDataNbtBackend
import us.timinc.mc.cobblemon.counter.storage.PlayerInstancedDataStores

object ServerStartingHandler {
    fun handle(starting: ServerStartedEvent) {
        val counterNbtFactory = CachedPlayerDataStoreFactory(CounterDataNbtBackend())
        counterNbtFactory.setup(starting.server)

        Cobblemon.playerDataManager.setFactory(counterNbtFactory, PlayerInstancedDataStores.COUNTER)
    }
}