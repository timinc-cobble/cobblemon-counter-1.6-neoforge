package us.timinc.mc.cobblemon.counter.command.argument

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.Component
import us.timinc.mc.cobblemon.counter.api.ScoreType
import us.timinc.mc.cobblemon.counter.api.ScoreTypeRegistry
import java.util.concurrent.CompletableFuture

class ScoreTypeArgument : ArgumentType<ScoreType> {
    companion object {
        fun type() = ScoreTypeArgument()

        fun getType(ctx: CommandContext<CommandSourceStack>, key: String): ScoreType =
            ctx.getArgument(key, ScoreType::class.java)
    }

    override fun parse(reader: StringReader): ScoreType {
        try {
            val type = reader.readString()
            return ScoreTypeRegistry.findByType(type)
        } catch (e: Error) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherParseException()
                .create(Component.translatable("cobbled_counter.command.error.score_type"))
        }
    }

    override fun <S> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder,
    ): CompletableFuture<Suggestions> =
        SharedSuggestionProvider.suggest(ScoreTypeRegistry.types(), builder)
}