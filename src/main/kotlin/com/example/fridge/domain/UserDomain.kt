package com.example.fridge.domain

import com.example.fridge.dto.UserRoleEnum
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document("users")
data class UserDocument(
    @Id
    val id: ObjectId = ObjectId(),
    val name: String,
    val surname: String? = null,
    val username: String,
    val password: String,
    val role: UserRoleEnum = UserRoleEnum.USER,
    val createdDate: Long = System.currentTimeMillis(),
    val modifiedDate: Long = System.currentTimeMillis(),
)

interface UserRepository : MongoRepository<UserDocument, String> {
    fun findFirstByUsername(username: String): UserDocument?
    fun existsByUsername(username: String): Boolean
}
