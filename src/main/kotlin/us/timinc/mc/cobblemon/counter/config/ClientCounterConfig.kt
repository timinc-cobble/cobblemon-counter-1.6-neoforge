package us.timinc.mc.cobblemon.counter.config

import us.timinc.mc.cobblemon.counter.api.CounterType

class ClientCounterConfig {
    val broadcast: Set<String> = CounterType.entries.map { it.type }.toSet()
}