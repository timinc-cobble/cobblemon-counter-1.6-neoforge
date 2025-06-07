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
import us.timinc.mc.cobblemon.counter.api.CounterType
import us.timinc.mc.cobblemon.counter.api.CounterTypeRegistry
import java.util.concurrent.CompletableFuture

class CounterTypeArgument : ArgumentType<CounterType> {
    companion object {
        fun type() = CounterTypeArgument()

        fun getType(ctx: CommandContext<CommandSourceStack>, key: String): CounterType =
            ctx.getArgument(key, CounterType::class.java)
    }

    override fun parse(reader: StringReader): CounterType {
        try {
            val type = reader.readString()
            return CounterTypeRegistry.findByType(type)
        } catch (e: Error) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherParseException()
                .create(Component.translatable("cobbled_counter.command.error.counter_type"))
        }
    }

    override fun <S> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder,
    ): CompletableFuture<Suggestions> =
        SharedSuggestionProvider.suggest(CounterTypeRegistry.types(), builder)
}