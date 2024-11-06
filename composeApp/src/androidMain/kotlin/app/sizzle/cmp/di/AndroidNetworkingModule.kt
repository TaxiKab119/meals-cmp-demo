package app.sizzle.cmp.di

import app.sizzle.cmp.core.data.createHttpClient
import app.sizzle.cmp.meals.data.MealsDbClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

val androidNetworkingModule = module {
    single { createHttpClient(OkHttp.create()) }
    single { MealsDbClient(get()) } // Inject Android Specific HttpClient into MealsDbClient
}