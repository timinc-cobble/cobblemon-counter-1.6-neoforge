package us.timinc.mc.cobblemon.counter.api

object CounterRegistry {
    val counterTypes: MutableList<CounterType> = CounterType.entries.toMutableList()

    fun registerCounterType(counterType: CounterType) {
        if (counterTypes.contains(counterType)) throw Error("${counterType.type} was already registered")
        counterTypes.add(counterType)
    }
}
