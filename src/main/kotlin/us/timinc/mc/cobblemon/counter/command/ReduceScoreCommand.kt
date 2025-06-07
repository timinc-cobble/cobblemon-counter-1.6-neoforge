package us.timinc.mc.cobblemon.counter.command

import com.cobblemon.mod.common.command.argument.SpeciesArgumentType
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.Commands.literal
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.CounterType
import us.timinc.mc.cobblemon.counter.api.ScoreType
import us.timinc.mc.cobblemon.counter.extensions.getCounterManager

object ReduceScoreCommand : AbstractCommand() {
    override fun define(): LiteralArgumentBuilder<CommandSourceStack> = literal("reduce").then(
        argument("species", SpeciesArgumentType.species()).then(
            argument("form", StringArgumentType.string()).then(
                argument("score", IntegerArgumentType.integer(0)).executes(::execute)
            )
        )
    )

    override fun run(
        ctx: CommandContext<CommandSourceStack>,
        player: ServerPlayer,
        counterType: CounterType,
        scoreType: ScoreType,
    ): Int {
        val species = SpeciesArgumentType.getPokemon(ctx, "species")
        val form = StringArgumentType.getString(ctx, "form")
        val score = IntegerArgumentType.getInteger(ctx, "score")

        val manager = player.getCounterManager()
        scoreType.adjustScore(manager, counterType, species.resourceIdentifier, form, -score)

        giveFeedback(
            Component.translatable(
                "cobbled_counter.command.feedback.reduce_score",
                player.name,
                score,
                Component.translatable("cobbled_counter.part.counter_type.${counterType.type}"),
                Component.translatable("cobbled_counter.part.score_type.${scoreType.type}")
            ), ctx
        )

        return Command.SINGLE_SUCCESS
    }
}