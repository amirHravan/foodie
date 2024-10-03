package com.ravan.foodie.domain.model

sealed class LoadableData<out T> {
    data object NotLoaded : LoadableData<Nothing>()

    data object Loading : LoadableData<Nothing>()

    class Loaded<T>(
        val data: T
    ) : LoadableData<T>()

    class Failed(
        val message: String,
    ) : LoadableData<Nothing>()
}