package us.timinc.mc.cobblemon.counter.api

import net.minecraft.resources.ResourceLocation

abstract class AbstractCounterManager {
    abstract val counters: Map<CounterType, Counter>
    fun getCounter(counterType: CounterType): Counter {
        return counters[counterType]
            ?: throw Error("${counterType.type} was not registered with the CounterRegistry before ")
    }

    fun getStreak(counterType: CounterType): Streak {
        return getCounter(counterType).streak
    }

    fun getStreakCount(counterType: CounterType, species: ResourceLocation? = null, form: String? = null): Int {
        val streak = getStreak(counterType)
        if (species === null) return streak.count
        if (form === null) return if (species == streak.species) streak.count else 0
        return if (species == streak.species && form == streak.form) streak.count else 0
    }

    fun getCount(counterType: CounterType, species: ResourceLocation? = null, form: String? = null): Int {
        val speciesRecord = getCounter(counterType).count[species] ?: return 0
        if (form === null) return speciesRecord.values.fold(0) { acc, element -> acc + element }
        return speciesRecord.getOrDefault(form, 0)
    }
}