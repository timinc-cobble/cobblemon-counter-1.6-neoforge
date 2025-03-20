@file:Suppress("MemberVisibilityCanBePrivate")

package us.timinc.mc.cobblemon.counter.config

import net.minecraft.resources.ResourceLocation
import us.timinc.mc.cobblemon.counter.api.CounterType

class CounterConfig {
    val debug: Boolean = false
    val breakStreakOnForm: List<String> = CounterType.entries.map { it.type }
    val formOverrides: List<FormOverride> = listOf(
        FormOverride(
            "cobblemon:aegislash",
            "Blade",
            "Normal"
        ),
        FormOverride(
            "cobblemon:minior",
            "Meteor",
            "Normal"
        ),
        FormOverride(
            "cobblemon:eiscue",
            "Noice-Face",
            "Normal"
        ),
        FormOverride(
            "cobblemon:morpeko",
            "Hangry",
            "Normal"
        ),
        FormOverride(
            "cobblemon:darmanitan",
            "Zen",
            "Normal"
        ),
        FormOverride(
            "cobblemon:darmanitan",
            "Galar-Zen",
            "Galar"
        ),
        FormOverride(
            "cobblemon:palafin",
            "Hero",
            "Normal"
        ),
        FormOverride(
            "cobblemon:wishiwashi",
            "School",
            "Normal"
        ),
        FormOverride(
            "cobblemon:giratina",
            "Origin",
            "Normal"
        ),
        FormOverride(
            "cobblemon:shaymin",
            "Sky",
            "Normal"
        ),
        FormOverride(
            "cobblemon:hoopa",
            "Unbound",
            "Normal"
        ),
        FormOverride(
            "cobblemon:meloetta",
            "Pirouette",
            "Normal"
        ),
        FormOverride(
            "cobblemon:necrozma",
            "Dusk-Mane",
            "Normal"
        ),
        FormOverride(
            "cobblemon:necrozma",
            "Dawn-Wings",
            "Normal"
        ),
        FormOverride(
            "cobblemon:necrozma",
            "Ultra",
            "Normal"
        ),
        FormOverride(
            "cobblemon:eternatus",
            "Eternamax",
            "Normal"
        ),
    )

    fun getFormOverride(fromSpecies: ResourceLocation, fromForm: String): FormOverride? =
        formOverrides.find { it.matches(fromSpecies, fromForm) }

    fun breakStreakOnForm(counterType: CounterType): Boolean = breakStreakOnForm.contains(counterType.type)
}