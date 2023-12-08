package com.example.fridge.config

import com.example.fridge.dto.EntityAlreadyExistsException
import com.example.fridge.dto.EntityNotFoundException
import com.example.fridge.dto.ExceptionDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException): ResponseEntity<ExceptionDto> {
        return ResponseEntity(ExceptionDto(ex.message), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EntityAlreadyExistsException::class)
    fun handleEntityAlreadyExists(ex: EntityAlreadyExistsException): ResponseEntity<ExceptionDto> {
        return ResponseEntity(ExceptionDto(ex.message), HttpStatus.CONFLICT)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionDto> {
        return ResponseEntity(
            ExceptionDto(ex.detailMessageArguments.joinToString()),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAnyException(ex: Exception): ResponseEntity<ExceptionDto> {
        return ResponseEntity(ExceptionDto(ex.message ?: ""), HttpStatus.BAD_REQUEST)
    }
}