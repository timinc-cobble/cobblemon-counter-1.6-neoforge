package us.timinc.mc.cobblemon.counter.scoretypes

import net.minecraft.resources.ResourceLocation
import us.timinc.mc.cobblemon.counter.api.CounterManager
import us.timinc.mc.cobblemon.counter.api.CounterType
import us.timinc.mc.cobblemon.counter.api.ScoreType

class StreakScoreType(type: String) : ScoreType(type) {
    override fun getScore(
        manager: CounterManager,
        counterType: CounterType,
        species: ResourceLocation?,
        formName: String?,
    ): Int = manager.getStreakScore(counterType, species, formName)

    override fun setScore(
        manager: CounterManager,
        counterType: CounterType,
        species: ResourceLocation,
        formName: String,
        score: Int,
    ) {
        manager.setStreakScore(counterType, species, formName, score)
    }
}