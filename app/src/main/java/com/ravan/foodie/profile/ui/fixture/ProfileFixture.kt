package com.ravan.foodie.profile.ui.fixture

import com.ravan.foodie.profile.api.dto.nurture.CreditChangeDto
import com.ravan.foodie.profile.api.dto.nurture.NurtureProfileDto
import com.ravan.foodie.profile.api.dto.nurture.NurtureProfileUserDto

val profileCreditTransferDto1 = CreditChangeDto(
    amount = 1000,
    createDate = "1400-05-10 00:00:00",
)


val profileCreditTransferDto2 = CreditChangeDto(
    amount = 50000,
    createDate = "1403-03-08 00:00:00",
)

val profileCreditTransferDto3 = CreditChangeDto(
    amount = 100000,
    createDate = "1402-07-15 00:00:00",
)
val profileNurtureProfileUserDto = NurtureProfileUserDto(
    firstName = "اصغر",
    lastName = "فرهادی",
    username = "400100400",
    enabled = true,
    gender = "MALE",
    id = 1,
    nationalCode = "00000000",
    personnel = false,
)

val profileNurtureProfileDto = NurtureProfileDto(
    createDate = "2021-09-01 00:00:00",
    credit = 1000,
    creditChanges = listOf(
        profileCreditTransferDto2,
        profileCreditTransferDto1,
        profileCreditTransferDto2,
        profileCreditTransferDto1,
        profileCreditTransferDto3,
    ),
    id = 1,
    nurtureProfileUserDto = profileNurtureProfileUserDto,
)