package com.ravan.foodie.reserveinfo.ui.fixture

import com.ravan.foodie.reserveinfo.ui.model.MealRowUIModel
import com.ravan.foodie.reserveinfo.ui.model.ReservationInfoCardUIModel
import com.ravan.foodie.reserveinfo.ui.model.ReservationInfoScreenUIModel

val reserveInfoMealRowUIModelFixture1 = MealRowUIModel(
    mealName = "ناهار",
    foodName = "خوراک کتلت",
    selfName = "مرکزی - سلف دانشجویان آقا",
    consumed = false,
)

val reserveInfoMealRowUIModelFixture2 = MealRowUIModel(
    mealName = "صبحانه",
    foodName = "شامی کباب",
    selfName = "مرکزی سلف دانشجویان آقا",
)

val reserveInfoMealRowUIModelFixture3 = MealRowUIModel(
    mealName = "ناهار",
    foodName = "کوفت و مرض",
    selfName = "مرکزی سلف دانشجویان آقا",
)

val reserveInfoMealRowUIModelFixture4 = MealRowUIModel(
    mealName = "شام",
    foodName = "درد بی‌درمون",
    selfName = "مرکزی سلف دانشجویان آقا",
)

val reserveInfoCardUIModelFixture = ReservationInfoCardUIModel(
    farsiDayName = "شنبه", farsiDate = "۱۴۰۰/۰۱/۰۱", mealRowUIModelList = listOf(
        reserveInfoMealRowUIModelFixture2,
        reserveInfoMealRowUIModelFixture3,
        reserveInfoMealRowUIModelFixture4,
    )
)

val reserveInfoScreenUIModelFixture = ReservationInfoScreenUIModel(
    reservationInfoCardUIModelList = listOf(
        reserveInfoCardUIModelFixture,
        reserveInfoCardUIModelFixture,
        reserveInfoCardUIModelFixture,
        reserveInfoCardUIModelFixture,
        reserveInfoCardUIModelFixture,
    )
)