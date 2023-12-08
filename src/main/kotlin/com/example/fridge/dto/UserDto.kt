package com.example.fridge.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UserCreate(
    @NotNull
    @NotBlank
    val name: String,
    val surname: String? = null,
    @NotNull
    @NotBlank
    @Size(min = 3, max = 10)
    val username: String,
    @NotNull
    @NotBlank
    @Size(min = 3, max = 18)
    val password: String,
    val role: UserRoleEnum = UserRoleEnum.ADMIN,
)

data class UserUpdate(
    @NotNull
    @NotBlank
    val name: String,
    val surname: String? = null,
    @NotNull
    @NotBlank
    @Size(min = 3, max = 18)
    val password: String,
    val role: UserRoleEnum,
)

data class UserResponse(
    val id: String,
    val name: String,
    val surname: String? = null,
    val username: String,
    val role: UserRoleEnum,
    val createdDate: Long,
    val modifiedDate: Long,
)