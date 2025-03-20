package us.timinc.mc.cobblemon.counter.event

import com.cobblemon.mod.common.api.events.Cancelable
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.resources.ResourceLocation
import us.timinc.mc.cobblemon.counter.api.CounterManager
import us.timinc.mc.cobblemon.counter.api.CounterType

interface BreakStreakEvent {
    val manager: CounterManager
    val counterType: CounterType
    val cause: Cause

    class Cause(
        val speciesId: ResourceLocation?,
        val formName: String?,
        val pokemon: Pokemon?,
    )

    class Pre(
        override val manager: CounterManager,
        override val counterType: CounterType,
        override val cause: Cause,
    ) : Cancelable(), BreakStreakEvent

    class Post(
        override val manager: CounterManager,
        override val counterType: CounterType,
        override val cause: Cause,
    ) : BreakStreakEvent
}