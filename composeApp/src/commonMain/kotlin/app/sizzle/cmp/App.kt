package app.sizzle.cmp

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import app.sizzle.cmp.meals.presentation.meal_detail.MealDetailRoot
import app.sizzle.cmp.meals.presentation.meal_detail.MealDetailViewModel
import app.sizzle.cmp.meals.presentation.meals_list.DisplayMealsRoot
import app.sizzle.cmp.meals.presentation.meals_list.DisplayMealsViewModel
import app.sizzle.cmp.navigation.DetailScreen
import app.sizzle.cmp.navigation.ListScreen
import app.sizzle.cmp.util.getAsyncImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }
        SharedTransitionLayout {

            val displayMealsViewModel: DisplayMealsViewModel = koinViewModel()

            val navController = rememberNavController()

            NavHost(navController, startDestination = ListScreen) {
                composable<ListScreen> {
                    DisplayMealsRoot(
                        viewModel = displayMealsViewModel,
                        onMealClick = { mealId ->
                            navController.navigate(DetailScreen(mealId = mealId))
                        },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable
                    )
                }
                composable<DetailScreen> { backStackEntry ->
                    val detailScreen: DetailScreen = backStackEntry.toRoute()
                    val mealDetailViewModel: MealDetailViewModel =
                        koinViewModel { parametersOf(detailScreen.mealId) }
                    MealDetailRoot(
                        viewModel = mealDetailViewModel,
                        onClose = { navController.popBackStack() },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable
                    )
                }
            }
        }
    }
}