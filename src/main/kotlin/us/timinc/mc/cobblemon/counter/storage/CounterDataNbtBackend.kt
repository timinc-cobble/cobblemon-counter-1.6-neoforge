package us.timinc.mc.cobblemon.counter.storage

import com.cobblemon.mod.common.api.storage.player.adapter.NbtBackedPlayerData
import us.timinc.mc.cobblemon.counter.api.CounterManager
import java.util.*

class CounterDataNbtBackend : NbtBackedPlayerData<CounterManager>("counter", PlayerInstancedDataStores.COUNTER) {
    override val codec = CounterManager.CODEC
    override val defaultData: (UUID) -> CounterManager
        get() = { uuid: UUID ->
            CounterManager(uuid)
        }
}