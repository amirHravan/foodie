package com.ravan.foodie.forget.ui.fixture

import com.ravan.foodie.forget.ui.model.ForgetCodeFoodDetailUIModel
import com.ravan.foodie.forget.ui.model.ForgetCodeItemUIModel
import com.ravan.foodie.forget.ui.model.ForgetCodeScreenUIModel
import com.ravan.foodie.order.domain.model.MealType

val forgetCodeDetailRow1 = ForgetCodeFoodDetailUIModel(
    foodName = "خوراک کتلت",
    selfName = "مرکزی - سلف دانشجویان آقا",
    forgetCode = null,
    reserveId = 123,
)

val forgetCodeDetailRow2 = ForgetCodeFoodDetailUIModel(
    foodName = "شنیتسل مرغ",
    selfName = "مرکزی - سلف دانشجویان آقا",
    forgetCode = "123123",
    reserveId = 123,
)

val forgetCodeItemCardUIModel = ForgetCodeItemUIModel(
    mealType = MealType.LUNCH,
    foodDetails = listOf(
        forgetCodeDetailRow1,
        forgetCodeDetailRow2
    ),
)

val forgetCodeScreenUIModelFixture = ForgetCodeScreenUIModel(
    forgetCodeItemUIModel = forgetCodeItemCardUIModel
)