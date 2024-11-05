package app.sizzle.cmp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import app.sizzle.cmp.meals.data.MealsDbClient
import app.sizzle.cmp.core.data.createHttpClient
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController {
    App(
        client = remember { MealsDbClient(createHttpClient(Darwin.create())) }
    )
}