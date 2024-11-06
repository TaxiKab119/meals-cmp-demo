package app.sizzle.cmp.di

import app.sizzle.cmp.core.data.createHttpClient
import app.sizzle.cmp.meals.data.MealsDbClient
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

val iosNetworkModule = module {
    single { createHttpClient(Darwin.create()) }
    single { MealsDbClient(get()) } // Inject iOS specific HttpClient into MealsDbClient
}