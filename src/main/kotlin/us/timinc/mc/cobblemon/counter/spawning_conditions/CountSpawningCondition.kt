package us.timinc.mc.cobblemon.counter.spawning_conditions

import com.cobblemon.mod.common.api.spawning.condition.AppendageCondition
import com.cobblemon.mod.common.api.spawning.context.SpawningContext
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.extensions.getCounterManager

class CountSpawningCondition : AppendageCondition {
    val counts: List<CountRequirement>? = null
    val streaks: List<CountRequirement>? = null

    override fun fits(ctx: SpawningContext): Boolean {
        val player = ctx.cause.entity as? ServerPlayer ?: return true
        val manager = player.getCounterManager()

        if (streaks !== null) streaks.forEach { req ->
            if (manager.getStreakCount(req.type, req.speciesRl, req.form) < req.amount) return false
        }

        if (counts !== null) counts.forEach { req ->
            if (manager.getCount(req.type, req.speciesRl, req.form) < req.amount) return false
        }

        return true
    }
}