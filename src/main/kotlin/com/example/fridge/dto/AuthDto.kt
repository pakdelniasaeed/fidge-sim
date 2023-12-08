package com.example.fridge.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


data class CustomUserDetails(
    val userDetailsInfo: UserDetailsInfo,
) : UserDetails {
    override fun getAuthorities() = userDetailsInfo.roles.map { SimpleGrantedAuthority("ROLE_${it.name}") }

    override fun getPassword() = userDetailsInfo.password

    override fun getUsername() = userDetailsInfo.username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}

data class UserDetailsInfo(
    val id: String,
    val name: String,
    val surname: String?,
    val username: String,
    val password: String,
    val roles: List<UserRoleEnum>,
    val createdDate: Long,
    val modifiedDate: Long,
)

enum class UserRoleEnum {
    SUPER_ADMIN,
    ADMIN,
    USER;

    fun getRolesList() = when (this) {
        SUPER_ADMIN -> listOf(USER, ADMIN, SUPER_ADMIN)
        ADMIN -> listOf(USER, ADMIN)
        USER -> listOf(USER)
    }

    companion object {
        fun fromRolesList(roles: List<UserRoleEnum>) = when {
            roles.contains(SUPER_ADMIN) -> SUPER_ADMIN
            roles.contains(ADMIN) -> ADMIN
            else -> USER
        }
    }
}

data class LoginRequest(
    @NotBlank
    @NotNull
    val username: String,
    @NotBlank
    @NotNull
    val password: String,
)

data class LoginResponse(
    val token: String,
)

