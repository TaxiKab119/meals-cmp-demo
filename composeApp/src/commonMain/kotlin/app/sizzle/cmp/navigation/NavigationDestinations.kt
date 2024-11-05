package app.sizzle.cmp.navigation

import kotlinx.serialization.Serializable

/**
 * Defines the possible navigation destinations for the app, using Kotlin DSL, allows for type safety
 */
@Serializable
object ListScreen
@Serializable
data class DetailScreen(val mealId: String)