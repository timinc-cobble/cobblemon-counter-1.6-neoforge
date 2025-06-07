package us.timinc.mc.cobblemon.counter.command

import com.cobblemon.mod.common.command.argument.SpeciesArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.Commands.argument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.counter.api.CounterType
import us.timinc.mc.cobblemon.counter.api.ScoreType
import us.timinc.mc.cobblemon.counter.extensions.getCounterManager

object GetFormScoreCommand : AbstractCommand() {
    override fun define(): LiteralArgumentBuilder<CommandSourceStack> = Commands.literal("get").then(
        argument("species", SpeciesArgumentType.species()).then(
            argument("form", StringArgumentType.string()).executes(
                ::execute
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
        val formName = StringArgumentType.getString(ctx, "form")

        val manager = player.getCounterManager()
        val score = scoreType.getScore(manager, counterType, species.resourceIdentifier, formName)

        giveFeedback(
            Component.translatable(
                "cobbled_counter.command.feedback.get_score.form",
                player.name,
                score,
                Component.translatable("cobbled_counter.part.counter_type.${counterType.type}"),
                Component.translatable("cobbled_counter.part.score_type.${scoreType.type}"),
                species.translatedName,
                formName
            ), ctx
        )

        return score
    }
}