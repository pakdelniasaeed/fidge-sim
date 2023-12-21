package com.example.fridge.controller

import com.example.fridge.domain.TestDataDocument
import com.example.fridge.domain.TestDocument
import com.example.fridge.dto.PagingRequest
import com.example.fridge.dto.PagingResponse
import com.example.fridge.dto.TestCreate
import com.example.fridge.service.TestService
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@ApiMapping
class TestController(
    private val testService: TestService,
) {

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("test")
    fun getAll(@Valid @ParameterObject paging: PagingRequest): ResponseEntity<PagingResponse<TestDocument>> {
        return ResponseEntity(testService.getAll(paging), HttpStatus.OK)
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("test/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<TestDocument> {
        return ResponseEntity(testService.getById(id), HttpStatus.OK)
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("test/{id}/test-data")
    fun getAllTestData(@PathVariable id: String): ResponseEntity<List<TestDataDocument>> {
        return ResponseEntity(testService.getAllTestData(id), HttpStatus.OK)
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping("test")
    fun save(@Valid @RequestBody create: TestCreate): ResponseEntity<TestDocument> {
        return ResponseEntity(testService.save(create), HttpStatus.CREATED)
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("test/{id}")
    fun update(@PathVariable id: String, @Valid @RequestBody update: TestCreate): ResponseEntity<TestDocument?> {
        return ResponseEntity(testService.update(id, update), HttpStatus.OK)
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping("test/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Unit> {
        return ResponseEntity(testService.delete(id), HttpStatus.OK)
    }
}