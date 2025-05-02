package us.timinc.mc.cobblemon.counter.item

import com.cobblemon.mod.common.client.tooltips.TooltipGenerator
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import us.timinc.mc.cobblemon.counter.CounterModClient
import us.timinc.mc.cobblemon.counter.api.CounterType

object CounterTooltipGenerator : TooltipGenerator() {
    override fun generateTooltip(stack: ItemStack, lines: MutableList<Component>): MutableList<Component> {
        val resultLines = mutableListOf<Component>()
        if (!stack.`is`(CounterItems.COUNTER)) {
            return resultLines
        }

        try {
            val mode = if (CounterItem.CLIENT_SPECIES === null) "streak" else "count"
            resultLines.add(Component.translatable("cobbled_counter.item.counter.tooltip.$mode"))
            CounterType.entries.forEach {
                val count: Int
                val species: ResourceLocation
                val form: String

                if (mode == "streak") {
                    val streak = CounterModClient.clientCounterData.getStreak(it)
                    count = streak.count
                    species = streak.species
                    form = streak.form

                    if (count == 0) {
                        resultLines.add(Component.translatable("cobbled_counter.item.counter.tooltip.${it.type}.no_streak"))
                        return@forEach
                    }
                } else {
                    count =
                        CounterModClient.clientCounterData.getCount(
                            it,
                            CounterItem.CLIENT_SPECIES,
                            CounterItem.CLIENT_FORM
                        )
                    species = CounterItem.CLIENT_SPECIES ?: return@forEach
                    form = CounterItem.CLIENT_FORM ?: return@forEach
                }

                resultLines.add(
                    Component.translatable(
                        "cobbled_counter.item.counter.tooltip.${it.type}.$mode",
                        count,
                        Component.translatable("cobblemon.species.${species.path}.name"),
                        if (form == "Normal") "" else Component.translatable(
                            "cobbled_counter.item.counter.tooltip.form",
                            form
                        )
                    )
                )
            }
            return resultLines
        } catch (e: Error) {
            return resultLines
        }
    }

    override fun generateAdditionalTooltip(stack: ItemStack, lines: MutableList<Component>): MutableList<Component> {
        val resultLines = mutableListOf<Component>()
        if (!stack.`is`(CounterItems.COUNTER)) {
            return resultLines
        }

        val otherMode = if (CounterItem.CLIENT_SPECIES === null) "count" else "streak"
        resultLines.add(Component.translatable("cobbled_counter.item.counter.tooltip.switch_mode_info.to_$otherMode"))
        return resultLines
    }
}