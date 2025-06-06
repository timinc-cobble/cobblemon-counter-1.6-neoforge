package us.timinc.mc.cobblemon.counter

import com.cobblemon.mod.common.client.tooltips.TooltipManager
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import us.timinc.mc.cobblemon.counter.CounterMod.MOD_ID
import us.timinc.mc.cobblemon.counter.api.ClientCounterManager
import us.timinc.mc.cobblemon.counter.config.ClientCounterConfig
import us.timinc.mc.cobblemon.counter.config.ConfigBuilder
import us.timinc.mc.cobblemon.counter.item.CounterTooltipGenerator
import us.timinc.mc.cobblemon.counter.storage.PlayerInstancedDataStores

@Mod(MOD_ID)
object CounterModClient {
    var clientCounterData: ClientCounterManager = ClientCounterManager(mutableMapOf(), emptySet())
    var config: ClientCounterConfig = ConfigBuilder.load(ClientCounterConfig::class.java, "${MOD_ID}_client")

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    object ModRegistration {
        @SubscribeEvent
        fun onSetup(e: FMLClientSetupEvent) {
            PlayerInstancedDataStores.COUNTER
            TooltipManager.registerTooltipGenerator(CounterTooltipGenerator)
        }
    }
}