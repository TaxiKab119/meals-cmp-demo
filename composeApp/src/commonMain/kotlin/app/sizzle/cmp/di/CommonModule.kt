package app.sizzle.cmp.di

import app.sizzle.cmp.meals.presentation.meal_detail.MealDetailViewModel
import app.sizzle.cmp.meals.presentation.meals_list.DisplayMealsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val commonModule = module {
    // Provide DisplayMealsViewModel with Koin's viewModel scope
    viewModel { DisplayMealsViewModel(client = get()) }
    // Provide MealDetailViewModel with Koin's viewModel scope
    viewModel { MealDetailViewModel(client = get()) }
}