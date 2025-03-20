package us.timinc.mc.cobblemon.counter.config

import net.minecraft.resources.ResourceLocation

data class FormOverride(
    val fromSpecies: String,
    val fromForm: String,
    val toForm: String,
    val toSpecies: String = fromSpecies,
) {
    fun matches(species: ResourceLocation, form: String): Boolean {
        return ResourceLocation.parse(fromSpecies) == species && form == fromForm
    }
}