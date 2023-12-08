package com.example.fridge.service

import com.example.fridge.domain.UserDocument
import com.example.fridge.domain.UserRepository
import com.example.fridge.dto.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun getAll(): List<UserResponse> {
        return userRepository.findAll().map {
            UserResponse(
                id = it.id.toHexString(),
                name = it.name,
                surname = it.surname,
                username = it.username,
                role = it.role,
                createdDate = it.createdDate,
                modifiedDate = it.modifiedDate,
            )
        }
    }

    fun getCurrent(user: CustomUserDetails): UserResponse {
        return UserResponse(
            id = user.userDetailsInfo.id,
            name = user.userDetailsInfo.name,
            surname = user.userDetailsInfo.surname,
            username = user.userDetailsInfo.username,
            role = UserRoleEnum.fromRolesList(user.userDetailsInfo.roles),
            createdDate = user.userDetailsInfo.createdDate,
            modifiedDate = user.userDetailsInfo.modifiedDate,
        )
    }

    fun save(create: UserCreate) {
        if (userRepository.existsByUsername(create.username))
            throw EntityAlreadyExistsException("user with ${create.username} already exists!")

        userRepository.save(
            UserDocument(
                name = create.name,
                surname = create.surname,
                username = create.username,
                password = passwordEncoder.encode(create.password),
                role = create.role,
            )
        )
    }

    fun update(id: String, update: UserUpdate) {
        userRepository.findByIdOrNull(id)?.let { found ->
            userRepository.save(
                found.copy(
                    name = update.name,
                    surname = update.surname,
                    password = passwordEncoder.encode(update.password),
                    role = update.role,
                    modifiedDate = System.currentTimeMillis(),
                )
            )
        } ?: throw EntityNotFoundException("user with $id id not found!")
    }

    fun delete(id: String) {
        userRepository.deleteById(id)
    }
}