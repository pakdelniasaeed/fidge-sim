package com.example.fridge.dto

data class ExceptionDto(
    val message: String,
)

data class EntityNotFoundException(override val message: String) : RuntimeException(message)
data class EntityAlreadyExistsException(override val message: String) : RuntimeException(message)