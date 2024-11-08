package app.sizzle.cmp.meals.presentation.meal_detail

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade


val sizzleOrange = Color(0xFFFF8147)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MealDetailRoot(
    viewModel: MealDetailViewModel,
    onClose: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.mealDetailUiState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        MealsDetailScreen(
            uiState = uiState,
            modifier = Modifier.padding(innerPadding),
            onBackPress = onClose,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope
        )
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MealsDetailScreen(
    uiState: MealDetailUiState,
    onBackPress: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        var isInstructionsExpanded by remember { mutableStateOf(false) }
        var showBackButton by remember { mutableStateOf(true) }

        val scrollState = rememberLazyListState()

        // hide back button once image is hidden from scroll state
        LaunchedEffect(scrollState.firstVisibleItemIndex) {
            showBackButton = scrollState.firstVisibleItemIndex == 0
        }

        val largeRadialGradient = object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val biggerDimension = maxOf(size.height, size.width)
                return RadialGradientShader(
                    colors = listOf(Color.White, sizzleOrange),
                    center = size.center,
                    radius = biggerDimension / 2f,
                    colorStops = listOf(0f, 0.95f)
                )
            }
        }

        // Meal image that fills the top portion of the screen
        LazyColumn(
            state = scrollState,
            modifier = Modifier,
            contentPadding = PaddingValues(top = 0.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            item {
                Box(
                    Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .background(largeRadialGradient),
                    contentAlignment = Alignment.Center
                ) {
                    with(sharedTransitionScope) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalPlatformContext.current)
                                .data(uiState.mealDetails.strMealThumb)
                                .crossfade(true)
                                .placeholderMemoryCacheKey("shared-image-${uiState.mealDetails.idMeal}") // same key as shared element key
                                .memoryCacheKey("shared-image-${uiState.mealDetails.idMeal}") // same key as shared element key
                                .build(),
                            contentDescription = uiState.mealDetails.strMeal,
                            modifier = Modifier
                                .height(250.dp)
                                .aspectRatio(1f)
                                .sharedElement(
                                    rememberSharedContentState(
                                        key = "shared-image-${uiState.mealDetails.idMeal}"
                                    ),
                                    animatedVisibilityScope = animatedContentScope
                                )
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                }
            }

            // Content Section (Add meal title, instructions, etc.)
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    with(sharedTransitionScope) {
                        Text(
                            text = uiState.mealDetails.strMeal,
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.sharedBounds(
                                rememberSharedContentState(key = "shared-text-${uiState.mealDetails.idMeal}"),
                                animatedVisibilityScope = animatedContentScope
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${uiState.mealDetails.strCategory} - ${uiState.mealDetails.strArea}",
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                }
            }

            // Instructions Section with Expandable "See More" Button
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Instructions with limited lines and "See More" option
                    Text(
                        text = uiState.mealDetails.strInstructions,
                        style = MaterialTheme.typography.body2,
                        maxLines = if (isInstructionsExpanded) Int.MAX_VALUE else 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.animateContentSize()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    // "See More" / "See Less" Button
                    TextButton(
                        onClick = { isInstructionsExpanded = !isInstructionsExpanded },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = if (isInstructionsExpanded) "See Less" else "See More",
                            color = sizzleOrange
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Display ingredients
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    val ingredients = listOf(
                        uiState.mealDetails.strIngredient1,
                        uiState.mealDetails.strIngredient2,
                        uiState.mealDetails.strIngredient3,
                        uiState.mealDetails.strIngredient4,
                        uiState.mealDetails.strIngredient5,
                        uiState.mealDetails.strIngredient6,
                        uiState.mealDetails.strIngredient7,
                        uiState.mealDetails.strIngredient8,
                        uiState.mealDetails.strIngredient9
                    ).filterNotNull()
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ingredients.forEach { ingredient ->
                            IngredientPill(ingredient)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = showBackButton
        ) {
            // Back Button - Appears over image, fades as you scroll past threshold
            IconButton(
                onClick = onBackPress,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .size(48.dp)
                    .background(Color.Black.copy(alpha = 0.4f), shape = MaterialTheme.shapes.small)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun IngredientPill(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = sizzleOrange,
                shape = CircleShape
            )
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        )
    }
}