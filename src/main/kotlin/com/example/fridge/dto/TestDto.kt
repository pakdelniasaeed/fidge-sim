package com.example.fridge.dto

import com.example.fridge.domain.TestDocument
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class TestCreate(
    @field:NotNull
    @field:NotBlank
    val title: String,
    @field:NotNull
    @field:NotBlank
    val deviceSerial: String,
    val timePeriodInMillis: Long = 86400000L, // = 24 hours
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
) {
    fun toDocument() = TestDocument(
        title = title,
        deviceSerial = deviceSerial,
        timePeriodInMillis = timePeriodInMillis,
        paramREFHeaterPower = paramREFHeaterPower,
        paramFRZHeaterPower = paramFRZHeaterPower,
        paramICEHeaterPower = paramICEHeaterPower,
        paramMaxDEFTime = paramMaxDEFTime,
        paramMinDEFTime = paramMinDEFTime,
        paramMaxTimeOfCompressor = paramMaxTimeOfCompressor,
        paramNoLoadPower = paramNoLoadPower,
        paramSettingPointMid = paramSettingPointMid,
        paramSettingPointHigh = paramSettingPointHigh,
        paramSettingPointLow = paramSettingPointLow,
        paramREFDefrostEndTemperature = paramREFDefrostEndTemperature,
        paramFRZDefrostEndTemperature = paramFRZDefrostEndTemperature,
    )
}

data class OpenapiTestDataCreate(
    @get:JsonProperty("Serial")
    val Serial: String,
    @get:JsonProperty("REF_EN_SEN_1")
    val REF_EN_SEN_1: Int?,
    @get:JsonProperty("REF_EN_SEN_2")
    val REF_EN_SEN_2: Int?,
    @get:JsonProperty("REF_EV_SEN")
    val REF_EV_SEN: Int?,
    @get:JsonProperty("EXT_SEN")
    val EXT_SEN: Int?,
    @get:JsonProperty("FRZ_EN_SEN_1")
    val FRZ_EN_SEN_1: Int?,
    @get:JsonProperty("FRZ_EN_SEN_2")
    val FRZ_EN_SEN_2: Int?,
    @get:JsonProperty("FRZ_EV_SEN")
    val FRZ_EV_SEN: Int?,
    @get:JsonProperty("ICE_SEN")
    val ICE_SEN: Int?,
    @get:JsonProperty("HMD")
    val HMD: Int?,
    @get:JsonProperty("CURRENT")
    val CURRENT: Int?,
    @get:JsonProperty("VOLTAGE")
    val VOLTAGE: Int?,
    @get:JsonProperty("AP")
    val AP: Int?,
    @get:JsonProperty("PF")
    val PF: Int?,
)
