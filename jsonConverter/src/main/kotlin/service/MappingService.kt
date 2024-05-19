import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.nio.charset.StandardCharsets

object MappingService {

    fun loadCsvMapping(csvFilePath: String): Map<String, String> {
        val mapping = mutableMapOf<String, String>()
        val csvFile = File(csvFilePath)
        CSVParser.parse(csvFile, StandardCharsets.UTF_8, CSVFormat.DEFAULT.withFirstRecordAsHeader()).use { parser ->
            val headerMap = parser.headerMap.mapKeys { it.key.trim() }
            val campoIndex = headerMap["CAMPO"] ?: throw IllegalArgumentException("CSV file does not contain 'CAMPO' column")
            val campoJsonIndex = headerMap["CAMPO(JSON)"] ?: throw IllegalArgumentException("CSV file does not contain 'CAMPO(JSON)' column")
            for (record in parser) {
                val campo = record.get(campoIndex).trim()
                val campoJson = record.get(campoJsonIndex).trim()
                mapping[campo] = campoJson
            }
        }
        return mapping
    }

    fun loadExcelMapping(excelFilePath: String): Map<String, String> {
        val mapping = mutableMapOf<String, String>()
        File(excelFilePath).inputStream().use { inputStream ->
            val workbook = WorkbookFactory.create(inputStream)
            val sheet = workbook.getSheetAt(0)
            val headerRow = sheet.getRow(0)
            val headerMap = mutableMapOf<String, Int>()
            for (cell in headerRow) {
                val cellValue = cell.stringCellValue.trim()
                headerMap[cellValue] = cell.columnIndex
            }
            val campoIndex = headerMap["CAMPO"]
            val campoJsonIndex = headerMap["CAMPO(JSON)"]
            if (campoIndex == null || campoJsonIndex == null) {
                throw IllegalArgumentException("Excel file does not contain 'CAMPO' or 'CAMPO(JSON)' column")
            }
            for (row in sheet) {
                if (row.rowNum == 0) continue
                val campo = row.getCell(campoIndex).stringCellValue.trim()
                val campoJson = row.getCell(campoJsonIndex).stringCellValue.trim()
                mapping[campo] = campoJson
            }
        }
        return mapping
    }
}
