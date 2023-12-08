package com.example.fridge.config

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.util.StringUtils
import java.security.Key
import java.util.*
import java.util.logging.Logger


object JwtUtils {

    private val logger: Logger = Logger.getLogger(this.javaClass.name)

    fun generateJwtToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(Long.MAX_VALUE))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact()
    }

    private fun key(): Key {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode("======================Fridge==========================="))
    }

    fun getUsernameFromJwtToken(token: String): String {
        return Jwts.parserBuilder().setSigningKey(key()).build()
            .parseClaimsJws(token).body.subject
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken)
            return true
        } catch (e: MalformedJwtException) {
            logger.severe("Invalid JWT token: {${e.message}}")
        } catch (e: ExpiredJwtException) {
            logger.severe("JWT token is expired: {${e.message}}")
        } catch (e: UnsupportedJwtException) {
            logger.severe("JWT token is unsupported: {${e.message}}")
        } catch (e: IllegalArgumentException) {
            logger.severe("JWT claims string is empty: {${e.message}}")
        }
        return false
    }

    fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        return if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7, headerAuth.length)
        } else null
    }
}