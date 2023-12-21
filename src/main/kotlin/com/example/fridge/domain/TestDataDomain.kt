package com.example.fridge.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document("test-data")
data class TestDataDocument(
    @Id
    val id: String = ObjectId().toHexString(),
    val testId: String,
    val dataREF_EN_SEN_1: Int?,
    val dataREF_EN_SEN_2: Int?,
    val dataREF_EV_SEN: Int?,
    val dataEXT_SEN: Int?,
    val dataFRZ_EN_SEN_1: Int?,
    val dataFRZ_EN_SEN_2: Int?,
    val dataFRZ_EV_SEN: Int?,
    val dataICE_SEN: Int?,
    val dataHMD: Int?,
    val dataCURRENT: Int?,
    val dataVOLTAGE: Int?,
    val dataAP: Int?,
    val dataPF: Int?,
    val createdDate: Long = System.currentTimeMillis(),
    val modifiedDate: Long = System.currentTimeMillis(),
)

interface TestDataRepository : MongoRepository<TestDataDocument, String> {
    fun findAllByTestId(testId: String): List<TestDataDocument>
    fun existsByTestId(testId: String): Boolean
    fun deleteAllByTestId(testId: String)
}
