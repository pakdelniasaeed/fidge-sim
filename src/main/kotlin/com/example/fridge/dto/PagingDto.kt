package com.example.fridge.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class PagingRequest(
    @field:NotNull
    @field:Min(0L)
    val page: Int = 0,
    @field:NotNull
    @field:Min(0L)
    @field:Max(50L)
    val size: Int = 10,
)

data class PagingResponse<T>(
    val list: List<T>,
    val total: Int,
    val hasMore: Boolean
) {
    constructor(
        list: List<T>,
        page: Int,
        size: Int,
        total: Int
    ) : this(
        list = list,
        total = total,
        hasMore = hasMore(page, size, total)
    )

    companion object {
        fun <T> empty() = PagingResponse<T>(emptyList(), 0, false)
    }
}

fun hasMore(page: Int, size: Int, total: Int) = total > (page + 1) * size
