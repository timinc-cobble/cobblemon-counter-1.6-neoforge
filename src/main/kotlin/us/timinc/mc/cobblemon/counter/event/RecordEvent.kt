package us.timinc.mc.cobblemon.counter.event

import com.cobblemon.mod.common.api.events.Cancelable
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.resources.ResourceLocation
import us.timinc.mc.cobblemon.counter.api.CounterManager
import us.timinc.mc.cobblemon.counter.api.CounterType

interface RecordEvent {
    val manager: CounterManager
    val counterType: CounterType
    val speciesId: ResourceLocation
    val formName: String
    val pokemon: Pokemon?

    class Pre(
        override val manager: CounterManager,
        override val counterType: CounterType,
        override val speciesId: ResourceLocation,
        override val formName: String,
        override val pokemon: Pokemon?,
    ) : Cancelable(), RecordEvent

    class Post(
        override val manager: CounterManager,
        override val counterType: CounterType,
        override val speciesId: ResourceLocation,
        override val formName: String,
        override val pokemon: Pokemon?,
    ) : RecordEvent
}