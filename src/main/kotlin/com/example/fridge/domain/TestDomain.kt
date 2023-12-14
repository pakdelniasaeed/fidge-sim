package com.example.fridge.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document("tests")
data class TestDocument(
    @Id
    val id: String = ObjectId().toHexString(),
    val title: String,
    val deviceSerial: String,
    val status: TestStatusEnum = TestStatusEnum.WAITING,
    val paramREFHeaterPower: Int?, // unit = Watt
    val paramFRZHeaterPower: Int?, // unit = Watt
    val paramICEHeaterPower: Int?, // unit = Watt
    val paramMaxDEFTime: Int?, // unit = Minute
    val paramMinDEFTime: Int?, // unit = Minute
    val paramMaxTimeOfCompressor: Int?, // unit = Minute
    val paramNoLoadPower: Int?, // unit = Minute
    val paramSettingPointMid: Int?, // unit = Celsius
    val paramSettingPointHigh: Int?, // unit = Celsius
    val paramSettingPointLow: Int?, // unit = Celsius
    val paramREFDefrostEndTemperature: Int?, // unit = Celsius
    val paramFRZDefrostEndTemperature: Int?, // unit = Celsius
    val createdDate: Long = System.currentTimeMillis(),
    val modifiedDate: Long = System.currentTimeMillis(),
)

interface TestRepository : MongoRepository<TestDocument, String> {
    fun findFirstByDeviceSerialAndStatusIn(deviceSerial: String, statusList: List<TestStatusEnum>): TestDocument?
}

enum class TestStatusEnum {
    WAITING, STARTED, FAILED, DONE;

    companion object {
        fun getPendingList() = listOf(WAITING, STARTED)
    }
}
