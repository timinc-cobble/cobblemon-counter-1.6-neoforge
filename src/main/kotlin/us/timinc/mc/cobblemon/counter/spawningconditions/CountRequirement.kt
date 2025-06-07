package us.timinc.mc.cobblemon.counter.spawningconditions

import com.cobblemon.mod.common.util.asIdentifierDefaultingNamespace
import net.minecraft.resources.ResourceLocation
import us.timinc.mc.cobblemon.counter.api.CounterType

data class CountRequirement(
    val type: CounterType,
    val amount: Int,
    val species: String,
    val form: String?,
) {
    val speciesRl: ResourceLocation
        get() = species.asIdentifierDefaultingNamespace()
}
