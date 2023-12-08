package com.example.fridge.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class AuthEntryPointJwt : AuthenticationEntryPoint {
    private val logger: Logger = Logger.getLogger(this.javaClass.name)

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        logger.severe("Unauthorized error: {${authException.message}}")
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized")
    }
}