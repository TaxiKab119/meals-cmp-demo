package app.sizzle.cmp.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            level = LogLevel.ALL // ensures the entire request is logged
        }

        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true // this ensures that if the api call returns some json fields that we have not declared in our data class that it doesn't just fail
                }
            )
        }
    }
}