package us.timinc.mc.cobblemon.counter.api

object CounterTypeRegistry {
    private val counterTypes: MutableMap<String, CounterType> = mutableMapOf()

    fun registerCounterType(counterType: CounterType): CounterType {
        if (counterTypes.containsKey(counterType.type)) throw Error("Attempted to register Counter type ${counterType.type} twice")
        counterTypes[counterType.type] = counterType
        return counterType
    }

    fun findByType(type: String): CounterType = counterTypes[type]!!
    fun types(): List<String> = counterTypes.keys.toList()
    fun counterTypes(): List<CounterType> = counterTypes.values.toList()
}
