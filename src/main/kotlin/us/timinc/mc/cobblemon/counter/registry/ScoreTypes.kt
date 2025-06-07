package us.timinc.mc.cobblemon.counter.registry

import us.timinc.mc.cobblemon.counter.api.ScoreType
import us.timinc.mc.cobblemon.counter.api.ScoreTypeRegistry
import us.timinc.mc.cobblemon.counter.scoretypes.CountScoreType
import us.timinc.mc.cobblemon.counter.scoretypes.StreakScoreType

object ScoreTypes {
    val COUNT = register(CountScoreType("count"))
    val STREAK = register(StreakScoreType("streak"))

    fun register(scoreType: ScoreType): ScoreType = ScoreTypeRegistry.registerScoreType(scoreType)
}