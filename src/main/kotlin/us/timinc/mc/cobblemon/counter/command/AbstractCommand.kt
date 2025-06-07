package us.timinc.mc.cobblemon.counter.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.CounterType
import us.timinc.mc.cobblemon.counter.api.ScoreType
import us.timinc.mc.cobblemon.counter.command.argument.CounterTypeArgument
import us.timinc.mc.cobblemon.counter.command.argument.ScoreTypeArgument

abstract class AbstractCommand {
    abstract fun define(): LiteralArgumentBuilder<CommandSourceStack>
    abstract fun run(
        ctx: CommandContext<CommandSourceStack>,
        player: ServerPlayer,
        counterType: CounterType,
        scoreType: ScoreType,
    ): Int

    fun execute(ctx: CommandContext<CommandSourceStack>): Int {
        val player = EntityArgument.getPlayer(ctx, "player")
        val counterType = CounterTypeArgument.getType(ctx, "counterType")
        val scoreType = ScoreTypeArgument.getType(ctx, "scoreType")

        return run(ctx, player, counterType, scoreType)
    }

    fun giveFeedback(component: Component, ctx: CommandContext<CommandSourceStack>) =
        ctx.source.player?.sendSystemMessage(component)
}