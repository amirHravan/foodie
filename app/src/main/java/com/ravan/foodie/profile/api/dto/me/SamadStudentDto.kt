package com.ravan.foodie.profile.api.dto.me

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SamadStudentDto(
    @SerialName("educationalStatus") val samadEducationalStatusDto: SamadEducationalStatusDto,
    @SerialName("passedSemesters") val passedSemesters: Int,
    @SerialName("semester") val semester: String,
    @SerialName("studentNumber") val studentNumber: String,
    @SerialName("studyLevel") val samadStudyLevelDto: SamadStudyLevelDto,
    @SerialName("studyMajor") val samadStudyMajorDto: SamadStudyMajorDto
)