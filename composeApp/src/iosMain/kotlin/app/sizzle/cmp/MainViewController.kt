package app.sizzle.cmp

import androidx.compose.ui.window.ComposeUIViewController
import app.sizzle.cmp.di.commonModule
import app.sizzle.cmp.di.initKoin
import app.sizzle.cmp.di.iosNetworkModule

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin(iosNetworkModule, commonModule) }
) {
    App()
}