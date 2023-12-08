package com.example.fridge

import com.example.fridge.config.ApplicationContextProvider
import com.example.fridge.domain.UserRepository
import com.example.fridge.dto.UserCreate
import com.example.fridge.dto.UserRoleEnum
import com.example.fridge.service.UserService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import java.time.ZoneId
import java.util.*
import java.util.logging.Logger

@SpringBootApplication
class FridgeApplication

fun main() {
    // Spring Boot SetTimeZone Asia/Tehran
    Logger.getGlobal().info("set time zone = Asia/Tehran")
    TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Tehran")))
    SpringApplicationBuilder(FridgeApplication::class.java)
        .profiles(System.getenv("FRIDGE_PROFILE") ?: "local")
        .run()
    initData()
}

fun initData() {
    val context = ApplicationContextProvider().applicationContext()
    val userRepository = context.getBean(UserRepository::class.java)
    val userService = context.getBean(UserService::class.java)

    if (userRepository.findAll().isEmpty()) {
        userService.save(
            UserCreate(
                name = "Admin",
                username = "admin",
                password = "admin",
                role = UserRoleEnum.SUPER_ADMIN,
            )
        )
    }
}
