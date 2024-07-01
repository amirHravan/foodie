package com.ravan.foodie.domain.ui.model

sealed class FoodieButtonUIModel {
    class Icon(val iconRes: Int) : FoodieButtonUIModel()
    class General(val iconRes: Int?, val title: String?) : FoodieButtonUIModel()
}