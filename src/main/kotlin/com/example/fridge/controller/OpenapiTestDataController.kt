package com.example.fridge.controller

import com.example.fridge.domain.TestDataDocument
import com.example.fridge.dto.OpenapiTestDataCreate
import com.example.fridge.service.TestService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@OpenApiMapping
class OpenapiTestDataController(
    private val testService: TestService,
) {

    @PostMapping("test-data")
    fun save(@RequestBody create: OpenapiTestDataCreate): ResponseEntity<TestDataDocument> {
        return ResponseEntity(testService.saveTestData(create), HttpStatus.CREATED)
    }
}