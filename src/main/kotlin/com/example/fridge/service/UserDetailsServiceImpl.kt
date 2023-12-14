package com.example.fridge.service

import com.example.fridge.domain.UserRepository
import com.example.fridge.dto.CustomUserDetails
import com.example.fridge.dto.UserDetailsInfo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findFirstByUsername(username)
            ?: throw UsernameNotFoundException("user with username $username not found")

        return CustomUserDetails(
            UserDetailsInfo(
                id = user.id,
                name = user.name,
                surname = user.surname,
                username = user.username,
                password = user.password,
                roles = user.role.getRolesList(),
                createdDate = user.createdDate,
                modifiedDate = user.modifiedDate,
            )
        )
    }
}