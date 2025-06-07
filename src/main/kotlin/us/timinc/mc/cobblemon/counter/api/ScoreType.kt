package us.timinc.mc.cobblemon.counter.api

import net.minecraft.resources.ResourceLocation
import kotlin.math.max

abstract class ScoreType(val type: String) {
    abstract fun getScore(
        manager: CounterManager,
        counterType: CounterType,
        species: ResourceLocation? = null,
        formName: String? = null,
    ): Int

    abstract fun setScore(
        manager: CounterManager,
        counterType: CounterType,
        species: ResourceLocation,
        formName: String,
        score: Int,
    )

    fun adjustScore(
        manager: CounterManager,
        counterType: CounterType,
        species: ResourceLocation,
        formName: String,
        score: Int,
    ) {
        val currentScore = getScore(manager, counterType, species, formName)
        setScore(manager, counterType, species, formName, max(0, currentScore + score))
    }
}