package app.sizzle.cmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import app.sizzle.cmp.networking.MealsDbClient
import app.sizzle.cmp.ui.DisplayMealsRoot
import app.sizzle.cmp.ui.DisplayMealsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(client: MealsDbClient) {
    MaterialTheme {
        DisplayMealsRoot(viewModel = DisplayMealsViewModel(client))
    }
}