package com.example.fridge.controller

import com.example.fridge.dto.LoginRequest
import com.example.fridge.dto.LoginResponse
import com.example.fridge.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiMapping
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("auth/login")
    fun login(@Valid @RequestBody req: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity(authService.login(req), HttpStatus.OK)
    }

}