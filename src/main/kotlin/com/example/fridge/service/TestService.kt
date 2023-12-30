package com.example.fridge.service

import com.example.fridge.domain.*
import com.example.fridge.dto.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TestService(
    private val testRepository: TestRepository,
    private val testDataRepository: TestDataRepository,
) {
    fun getAll(paging: PagingRequest): PagingResponse<TestDocument> {
        val res = testRepository.findAll(
            PageRequest.of(
                paging.page,
                paging.size,
                Sort.by(Sort.Direction.DESC, "createdDate")
            )
        )

        return PagingResponse(
            list = res.toList().map { checkStatusAndSave(it) },
            page = paging.page,
            size = paging.size,
            total = res.totalElements.toInt(),
        )
    }

    fun save(create: TestCreate): TestDocument {
        if (getProcessingTest(create.deviceSerial) != null)
            throw EntityAlreadyExistsException("can not create test when there is a WAITING/STARTED test with this device serial: ${create.deviceSerial}")
        return testRepository.save(create.toDocument())
    }

    private fun getProcessingTest(serial: String): TestDocument? {
        return testRepository.findFirstByDeviceSerialAndStatusIn(serial, TestStatusEnum.getProcessingList())
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
        return getProcessingTest(create.Serial)?.let { foundTest ->
            if (foundTest.status == TestStatusEnum.WAITING) {
                testRepository.save(foundTest.copy(status = TestStatusEnum.STARTED))
            }
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

    fun getById(id: String): TestDocument {
        return testRepository.findByIdOrNull(id)?.let { found ->
            checkStatusAndSave(test = found)
        } ?: throw EntityNotFoundException("test not found!")
    }

    fun checkStatusAndSave(test: TestDocument): TestDocument {
        return when {
            isFailed(test) -> testRepository.save(test.copy(status = TestStatusEnum.FAILED))
            isDone(test) -> testRepository.save(test.copy(status = TestStatusEnum.DONE))
            else -> test
        }
    }

    fun isFailed(test: TestDocument): Boolean {
        return test.status == TestStatusEnum.WAITING
                && ((System.currentTimeMillis() - test.createdDate) > (60 * 1000))
                && !testDataRepository.existsByTestId(testId = test.id)
    }

    fun isDone(test: TestDocument): Boolean {
        return test.status == TestStatusEnum.STARTED
                && ((System.currentTimeMillis() - test.createdDate) > test.timePeriodInMillis)
    }
}