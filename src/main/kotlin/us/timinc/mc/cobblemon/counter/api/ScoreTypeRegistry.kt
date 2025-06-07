package us.timinc.mc.cobblemon.counter.api

object ScoreTypeRegistry {
    private val scoreTypes: MutableMap<String, ScoreType> = mutableMapOf()

    fun registerScoreType(scoreType: ScoreType): ScoreType {
        if (scoreTypes.containsKey(scoreType.type)) throw Error("Attempted to register Score type ${scoreType.type} twice")
        scoreTypes[scoreType.type] = scoreType
        return scoreType
    }

    fun findByType(type: String): ScoreType = scoreTypes[type]!!
    fun types(): List<String> = scoreTypes.keys.toList()
}