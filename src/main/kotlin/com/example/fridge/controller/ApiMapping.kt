package com.example.fridge.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api", produces = [(MediaType.APPLICATION_JSON_VALUE)])
annotation class ApiMapping