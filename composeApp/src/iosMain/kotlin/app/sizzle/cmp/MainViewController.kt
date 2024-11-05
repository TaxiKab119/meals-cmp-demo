package app.sizzle.cmp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import app.sizzle.cmp.networking.MealsDbClient
import app.sizzle.cmp.networking.createHttpClient
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController {
    App(
        client = remember { MealsDbClient(createHttpClient(Darwin.create())) }
    )
}