package us.timinc.mc.cobblemon.counter

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.scheduling.ScheduledTask
import com.cobblemon.mod.common.api.scheduling.ServerTaskTracker
import com.cobblemon.mod.common.api.spawning.condition.AppendageCondition
import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition
import com.cobblemon.mod.common.api.storage.player.PlayerInstancedDataStoreType
import net.minecraft.commands.synchronization.ArgumentTypeInfos
import net.minecraft.commands.synchronization.SingletonArgumentInfo
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.RegisterEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import us.timinc.mc.cobblemon.counter.command.CounterCommands
import us.timinc.mc.cobblemon.counter.command.argument.CounterTypeArgument
import us.timinc.mc.cobblemon.counter.command.argument.ScoreTypeArgument
import us.timinc.mc.cobblemon.counter.config.ConfigBuilder
import us.timinc.mc.cobblemon.counter.config.CounterConfig
import us.timinc.mc.cobblemon.counter.event.handler.CounterEventHandlers
import us.timinc.mc.cobblemon.counter.item.CounterItems
import us.timinc.mc.cobblemon.counter.registry.CounterTypes
import us.timinc.mc.cobblemon.counter.registry.ScoreTypes
import us.timinc.mc.cobblemon.counter.spawningconditions.CountSpawningCondition
import us.timinc.mc.cobblemon.counter.storage.PlayerInstancedDataStores

@Mod(CounterMod.MOD_ID)
object CounterMod {
    const val MOD_ID = "cobbled_counter"

    val saveTasks = mutableMapOf<PlayerInstancedDataStoreType, ScheduledTask>()
    val commandArgumentTypes = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, MOD_ID)

    init {
        commandArgumentTypes.register("counter_type") { _ ->
            ArgumentTypeInfos.registerByClass(
                CounterTypeArgument::class.java,
                SingletonArgumentInfo.contextFree(CounterTypeArgument::type)
            )
        }
        commandArgumentTypes.register("score_type") { _ ->
            ArgumentTypeInfos.registerByClass(
                ScoreTypeArgument::class.java,
                SingletonArgumentInfo.contextFree(ScoreTypeArgument::type)
            )
        }
    }

    private var logger: Logger = LogManager.getLogger(MOD_ID)
    var config: CounterConfig

    init {
        with(MOD_BUS) {
            this@CounterMod.commandArgumentTypes.register(this)
            addListener(::registerItems)
            addListener(::onCreativeTabsModification)
        }
        AppendageCondition.registerAppendage(SpawningCondition::class.java, CountSpawningCondition::class.java)
        CounterEventHandlers.register()
        CounterCommands.register()
        CounterTypes
        config = ConfigBuilder.load(CounterConfig::class.java, MOD_ID)
        ScoreTypes
        saveTasks[PlayerInstancedDataStores.COUNTER] = ScheduledTask.Builder()
            .execute { Cobblemon.playerDataManager.saveAllOfOneType(PlayerInstancedDataStores.COUNTER) }
            .delay(30f)
            .interval(120f)
            .infiniteIterations()
            .tracker(ServerTaskTracker)
            .build()
    }

    fun registerItems(e: RegisterEvent) {
        CounterItems
    }

    fun onCreativeTabsModification(e: BuildCreativeModeTabContentsEvent) {
        CounterItems.registerTabs(e)
    }

    fun debug(msg: String) {
        if (!config.debug) return
        logger.info(msg)
    }

    fun modResource(path: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
    }
}