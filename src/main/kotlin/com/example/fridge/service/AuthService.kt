package com.example.fridge.service

import com.example.fridge.config.JwtUtils
import com.example.fridge.dto.LoginRequest
import com.example.fridge.dto.LoginResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
) {

    fun login(req: LoginRequest): LoginResponse {

        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(req.username, req.password)
        )

        return LoginResponse(
            token = JwtUtils.generateJwtToken(req.username)
        )
    }
}