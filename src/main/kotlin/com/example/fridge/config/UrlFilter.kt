package com.example.fridge.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*


@Configuration
class UrlFilterBean {
    @Bean
    fun urlFiltering(): FilterRegistrationBean<UrlFilter>? {
        val registrationBean: FilterRegistrationBean<UrlFilter> = FilterRegistrationBean()
        registrationBean.filter = UrlFilter()
        registrationBean.addUrlPatterns("/*")
        registrationBean.order = Int.MIN_VALUE
        return registrationBean
    }
}

class UrlFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.method.equals("options", true)) {
            response.status = 200
            response.addHeader("Access-Control-Allow-Origin", "*")
            response.addHeader("Access-Control-Allow-Credentials", "true")
            response.addHeader(
                "Access-Control-Allow-Headers",
                "Content-Type, Authorization"
            )
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, PATCH")
            response.addHeader("max-age", "")
        } else {
            if (request.requestURI.lowercase(Locale.getDefault()).startsWith("/api")) {
                if (request.requestURI.endsWith('/')) {
                    request.getRequestDispatcher(request.requestURI.trimEnd('/')).forward(request, response)
                } else {
                    filterChain.doFilter(request, response)
                }
            } else if (request.requestURI.contains('.') || // Loading files *.*
                request.requestURI.startsWith("/swagger-doc") ||
                request.requestURI == "/swagger" ||
                request.requestURI.lowercase(Locale.getDefault()).startsWith("/websocket")
            ) {
                filterChain.doFilter(request, response)
            } else {
                request.getRequestDispatcher("/").forward(request, response)
            }
        }
    }
}
