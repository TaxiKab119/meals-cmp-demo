package app.sizzle.cmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import app.sizzle.cmp.meals.data.MealsDbClient
import app.sizzle.cmp.meals.presentation.meal_detail.MealDetailRoot
import app.sizzle.cmp.meals.presentation.meal_detail.MealDetailViewModel
import app.sizzle.cmp.meals.presentation.meals_list.DisplayMealsRoot
import app.sizzle.cmp.meals.presentation.meals_list.DisplayMealsViewModel
import app.sizzle.cmp.navigation.DetailScreen
import app.sizzle.cmp.navigation.ListScreen
import app.sizzle.cmp.util.getAsyncImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(client: MealsDbClient) {
    MaterialTheme {
        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }

        val navController = rememberNavController()

        NavHost(navController, startDestination = ListScreen) {
            composable<ListScreen> {
                DisplayMealsRoot(
                    viewModel = DisplayMealsViewModel(client),
                    onMealClick = { mealId ->
                        navController.navigate(
                            DetailScreen(mealId = mealId)
                        )
                    }
                )
            }
            composable<DetailScreen> {backStackEntry ->
                val mealId: DetailScreen = backStackEntry.toRoute()
                MealDetailRoot(
                    viewModel = MealDetailViewModel(client),
                    mealId = mealId.mealId
                )
            }
        }
    }
}