package com.example.fridge.service

import com.example.fridge.domain.*
import com.example.fridge.dto.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TestService(
    private val testRepository: TestRepository,
    private val testDataRepository: TestDataRepository,
) {
    fun getAll(paging: PagingRequest): PagingResponse<TestDocument> {
        val res = testRepository.findAll(PageRequest.of(paging.page, paging.size))
        return PagingResponse(
            list = res.toList(),
            page = paging.page,
            size = paging.size,
            total = res.totalElements.toInt(),
        )
    }

    fun save(create: TestCreate): TestDocument {
        if (existsPendingTest(create.deviceSerial))
            throw EntityAlreadyExistsException("can not create test when there is a WAITING/STARTED test with this device serial: ${create.deviceSerial}")
        return testRepository.save(create.toDocument())
    }

    private fun existsPendingTest(serial: String): Boolean {
        return testRepository.findFirstByDeviceSerialAndStatusIn(serial, TestStatusEnum.getPendingList()) != null
    }

    fun update(id: String, update: TestCreate): TestDocument? {
        return testRepository.findByIdOrNull(id)?.let { found ->
            testRepository.save(
                update.toDocument().copy(
                    id = found.id,
                    modifiedDate = System.currentTimeMillis(),
                )
            )
        }
    }

    fun delete(id: String) {
        testRepository.deleteById(id)
        testDataRepository.deleteAllByTestId(id)
    }

    fun getAllTestData(id: String): List<TestDataDocument> {
        return testDataRepository.findAllByTestId(id)
    }

    fun saveTestData(create: OpenapiTestDataCreate): TestDataDocument? {
        return testRepository.findFirstByDeviceSerialAndStatusIn(
            create.Serial,
            TestStatusEnum.getPendingList()
        )?.let { foundTest ->
            testDataRepository.save(
                TestDataDocument(
                    testId = foundTest.id,
                    dataREF_EN_SEN_1 = create.REF_EN_SEN_1,
                    dataREF_EN_SEN_2 = create.REF_EN_SEN_2,
                    dataREF_EV_SEN = create.REF_EV_SEN,
                    dataEXT_SEN = create.EXT_SEN,
                    dataFRZ_EN_SEN_1 = create.FRZ_EN_SEN_1,
                    dataFRZ_EN_SEN_2 = create.FRZ_EN_SEN_2,
                    dataFRZ_EV_SEN = create.FRZ_EV_SEN,
                    dataICE_SEN = create.ICE_SEN,
                    dataHMD = create.HMD,
                    dataCURRENT = create.CURRENT,
                    dataVOLTAGE = create.VOLTAGE,
                    dataAP = create.AP,
                    dataPF = create.PF,
                )
            )
        }
            ?: throw EntityNotFoundException("there is no test with status WAITING/STARTED and device serial: ${create.Serial}")
    }
}