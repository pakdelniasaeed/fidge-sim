package com.example.fridge.service

import com.example.fridge.domain.TestDataDocument
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

@Service
class ExcelService() {

    fun generate(data: List<TestDataDocument>): ByteArray {
        val out = ByteArrayOutputStream()
        val workbook = XSSFWorkbook()
        val workSheet = workbook.createSheet("data")
        val headerRow = workSheet.createRow(0)

        var cellIndex = 0
        headerRow.createCell(cellIndex++).setCellValue("Time")
        headerRow.createCell(cellIndex++).setCellValue("REF_EN_SEN_1")
        headerRow.createCell(cellIndex++).setCellValue("REF_EN_SEN_2")
        headerRow.createCell(cellIndex++).setCellValue("REF_EV_SEN")
        headerRow.createCell(cellIndex++).setCellValue("EXT_SEN")
        headerRow.createCell(cellIndex++).setCellValue("FRZ_EN_SEN_1")
        headerRow.createCell(cellIndex++).setCellValue("FRZ_EN_SEN_2")
        headerRow.createCell(cellIndex++).setCellValue("FRZ_EV_SEN")
        headerRow.createCell(cellIndex++).setCellValue("ICE_SEN")
        headerRow.createCell(cellIndex++).setCellValue("HMD")
        headerRow.createCell(cellIndex++).setCellValue("CURRENT")
        headerRow.createCell(cellIndex++).setCellValue("VOLTAGE")
        headerRow.createCell(cellIndex++).setCellValue("AP")
        headerRow.createCell(cellIndex++).setCellValue("PF")

        data.forEachIndexed { index, item ->
            workSheet.createRow(index + 1).let { row ->
                cellIndex = 0
                row.createCell(cellIndex++).setCellValue((item.createdDate / 1000L).toString())
                row.createCell(cellIndex++).setCellValue(item.dataREF_EN_SEN_1?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataREF_EN_SEN_2?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataREF_EV_SEN?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataEXT_SEN?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataFRZ_EN_SEN_1?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataFRZ_EN_SEN_2?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataFRZ_EV_SEN?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataICE_SEN?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataHMD?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataCURRENT?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataVOLTAGE?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataAP?.toString() ?: "")
                row.createCell(cellIndex++).setCellValue(item.dataPF?.toString() ?: "")
            }
        }

        workbook.write(out)
        workbook.close()
        out.flush()

        return out.toByteArray()
    }
}
