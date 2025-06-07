package us.timinc.mc.cobblemon.counter.registry

import us.timinc.mc.cobblemon.counter.api.CounterType
import us.timinc.mc.cobblemon.counter.api.CounterTypeRegistry

object CounterTypes {
    val CAPTURE = register(CounterType("capture"))
    val KO = register(CounterType("ko"))
    val RESURRECTION = register(CounterType("resurrection"))
    val FISH = register(CounterType("fish"))
    val HATCH = register(CounterType("hatch"))

    fun register(counterType: CounterType): CounterType = CounterTypeRegistry.registerCounterType(counterType)
}