package com.example.fridge.controller

import com.example.fridge.domain.UserDocument
import com.example.fridge.dto.CustomUserDetails
import com.example.fridge.dto.UserCreate
import com.example.fridge.dto.UserResponse
import com.example.fridge.dto.UserUpdate
import com.example.fridge.service.UserService
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@ApiMapping
class UserController(
    private val userService: UserService,
) {

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("user")
    fun getAll(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity(userService.getAll(), HttpStatus.OK)
    }

    @GetMapping("user/current")
    fun getCurrentUser(@Parameter(hidden = true) @AuthenticationPrincipal user: CustomUserDetails): ResponseEntity<UserResponse> {
        return ResponseEntity(userService.getCurrent(user), HttpStatus.OK)
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("user")
    fun save(@Valid @RequestBody create: UserCreate): ResponseEntity<UserDocument> {
        return ResponseEntity(userService.save(create), HttpStatus.CREATED)
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("user/{id}")
    fun update(@PathVariable id: String, @Valid @RequestBody update: UserUpdate): ResponseEntity<UserDocument> {
        return ResponseEntity(userService.update(id, update), HttpStatus.OK)
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("user/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Unit> {
        return ResponseEntity(userService.delete(id), HttpStatus.OK)
    }
}