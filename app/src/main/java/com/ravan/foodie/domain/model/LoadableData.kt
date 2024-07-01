package com.ravan.foodie.domain.model

sealed class LoadableData {
    data object NotLoaded : LoadableData()

    data object Loading : LoadableData()

    class Loaded<T>(
        val data: T
    ) : LoadableData()

    class Failed(
        val message: String,
    ) : LoadableData()
}