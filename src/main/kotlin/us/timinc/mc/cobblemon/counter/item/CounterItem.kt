package us.timinc.mc.cobblemon.counter.item

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class CounterItem(properties: Properties) : Item(properties) {
    companion object {
        var CLIENT_SPECIES: ResourceLocation? = null
        var CLIENT_FORM: String? = null
    }

    override fun interactLivingEntity(
        itemStack: ItemStack,
        player: Player,
        livingEntity: LivingEntity,
        interactionHand: InteractionHand,
    ): InteractionResult {
        if (!player.level().isClientSide) return InteractionResult.PASS
        if (livingEntity !is PokemonEntity) return InteractionResult.PASS

        val pokemon = livingEntity.pokemon
        val species = pokemon.species.resourceIdentifier
        val form = pokemon.form.name

        CLIENT_SPECIES = species
        CLIENT_FORM = form

        return InteractionResult.SUCCESS
    }

    override fun use(
        level: Level,
        player: Player,
        interactionHand: InteractionHand,
    ): InteractionResultHolder<ItemStack> {
        val heldItem = player.getItemInHand(interactionHand)
        if (!heldItem.`is`(CounterItems.COUNTER)) return InteractionResultHolder(InteractionResult.PASS, heldItem)
        if (!level.isClientSide) return InteractionResultHolder(InteractionResult.PASS, heldItem)

        if (CLIENT_SPECIES != null) {
            CLIENT_SPECIES = null
            CLIENT_FORM = null
        }

        return InteractionResultHolder(InteractionResult.SUCCESS, heldItem)
    }
}