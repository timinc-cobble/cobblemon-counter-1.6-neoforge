package us.timinc.mc.cobblemon.counter

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.scheduling.ScheduledTask
import com.cobblemon.mod.common.api.scheduling.ServerTaskTracker
import com.cobblemon.mod.common.api.storage.player.PlayerInstancedDataStoreType
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import net.neoforged.neoforge.event.server.ServerStartedEvent
import net.neoforged.neoforge.registries.RegisterEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import us.timinc.mc.cobblemon.counter.api.CounterRegistry
import us.timinc.mc.cobblemon.counter.config.ConfigBuilder
import us.timinc.mc.cobblemon.counter.config.CounterConfig
import us.timinc.mc.cobblemon.counter.event.handler.CounterEventHandlers
import us.timinc.mc.cobblemon.counter.event.handler.ServerStartingHandler
import us.timinc.mc.cobblemon.counter.item.CounterItems
import us.timinc.mc.cobblemon.counter.storage.PlayerInstancedDataStores

@Mod(CounterMod.MOD_ID)
object CounterMod {
    const val MOD_ID = "cobbled_counter"

    val saveTasks = mutableMapOf<PlayerInstancedDataStoreType, ScheduledTask>()

    private var logger: Logger = LogManager.getLogger(MOD_ID)
    var config: CounterConfig = ConfigBuilder.load(CounterConfig::class.java, MOD_ID)
    var eventsInitialized = false

    @EventBusSubscriber
    object Registration {
        @SubscribeEvent
        fun onInit(e: ServerStartedEvent) {
            ServerStartingHandler.handle(e)
            CounterRegistry
            saveTasks[PlayerInstancedDataStores.COUNTER] = ScheduledTask.Builder()
                .execute { Cobblemon.playerDataManager.saveAllOfOneType(PlayerInstancedDataStores.COUNTER) }
                .delay(30f)
                .interval(120f)
                .infiniteIterations()
                .tracker(ServerTaskTracker)
                .build()
            if (eventsInitialized) return
            eventsInitialized = true
            CounterEventHandlers.register()
        }
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    object ModRegistration {
        @SubscribeEvent
        fun registerItems(e: RegisterEvent) {
            CounterItems
        }

        @SubscribeEvent
        fun onCreativeTabsModification(e: BuildCreativeModeTabContentsEvent) {
            CounterItems.registerTabs(e)
        }
    }

    fun debug(msg: String) {
        if (!config.debug) return
        logger.info(msg)
    }

    fun modResource(path: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
    }
}