package us.timinc.mc.cobblemon.counter.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.RegisterCommandsEvent
import us.timinc.mc.cobblemon.counter.command.argument.CounterTypeArgument
import us.timinc.mc.cobblemon.counter.command.argument.ScoreTypeArgument

object CounterCommands {
    fun register() {
        NeoForge.EVENT_BUS.addListener { e: RegisterCommandsEvent ->
            registerOneWithPlayerAndType(e.dispatcher, GetScoreCommand.define())
            registerOneWithPlayerAndType(e.dispatcher, GetSpeciesScoreCommand.define())
            registerOneWithPlayerAndType(e.dispatcher, GetFormScoreCommand.define())
            registerOneWithPlayerAndType(e.dispatcher, SetScoreCommand.define())
            registerOneWithPlayerAndType(e.dispatcher, AddScoreCommand.define())
            registerOneWithPlayerAndType(e.dispatcher, ReduceScoreCommand.define())
        }
    }

    private fun registerOneWithPlayerAndType(
        dispatcher: CommandDispatcher<CommandSourceStack>,
        command: LiteralArgumentBuilder<CommandSourceStack>,
    ) {
        dispatcher.register(
            Commands.literal("count").then(
                Commands.argument("player", EntityArgument.player()).then(
                    Commands.argument("counterType", CounterTypeArgument.type()).then(
                        Commands.argument("scoreType", ScoreTypeArgument.type()).then(command)
                    )
                )
            )
        )
    }
}