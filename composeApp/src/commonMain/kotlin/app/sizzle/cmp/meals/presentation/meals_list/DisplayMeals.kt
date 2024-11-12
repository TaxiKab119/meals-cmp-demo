package app.sizzle.cmp.meals.presentation.meals_list

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.sizzle.cmp.core.presentation.ErrorScreen
import app.sizzle.cmp.core.presentation.LoadingScreen
import app.sizzle.cmp.meals.data.Meal
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.vectorResource
import sizzlecmpdemo.composeapp.generated.resources.Res
import sizzlecmpdemo.composeapp.generated.resources.full_sizzle_logo

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DisplayMealsRoot(
    viewModel: DisplayMealsViewModel,
    onMealClick: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.displayMealsUiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.White
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = vectorResource(Res.drawable.full_sizzle_logo),
                        contentDescription = null,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        when (uiState.screenState) {
            ScreenState.LOADING -> {
                LoadingScreen()
            }

            ScreenState.ERROR -> ErrorScreen() { viewModel.getMealsData() }
            ScreenState.SUCCESS -> {
                DisplayMealsScreen(
                    uiState = uiState,
                    modifier = modifier.padding(innerPadding),
                    onMealClick = { onMealClick(it) },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DisplayMealsScreen(
    uiState: DisplayMealsUiState,
    onMealClick: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(uiState.meals) { meal ->
            MealItem(
                meal = meal,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            ) {
                onMealClick(it)
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MealItem(
    meal: Meal,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onClick: (String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(meal.idMeal)
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            with(sharedTransitionScope) {
                // Display the meal image
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(meal.strMealThumb)
                        .crossfade(true)
                        .placeholderMemoryCacheKey("shared-image-${meal.idMeal}") // same key as shared element key
                        .memoryCacheKey("shared-image-${meal.idMeal}") // same key as shared element key
                        .build(),
                    contentDescription = meal.strMeal,
                    modifier = Modifier
                        .height(175.dp)
                        .aspectRatio(1f)
                        .sharedElement(
                            rememberSharedContentState(
                                key = "shared-image-${meal.idMeal}"
                            ),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                bottomStart = 8.dp
                            )
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = meal.strMeal,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .sharedBounds(
                            rememberSharedContentState(key = "shared-text-${meal.idMeal}"),
                            animatedVisibilityScope = animatedContentScope,
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                        )
                )
            }
        }
    }
}