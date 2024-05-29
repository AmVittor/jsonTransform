
fun main() {
    val filePath = "path/to/csv"
    val jsonString = """
    {
        "NameCompany": "ZRPAS",
        "ValueEstimate": "120120",
        "Item": [
            {
             "ProductId": "197"
            }
        ]
    }
    """

    try {
        val mapping = if (filePath.endsWith(".csv")) {
            MappingService.loadCsvMapping(filePath)
        } else if (filePath.endsWith(".xlsx")) {
            MappingService.loadExcelMapping(filePath)
        } else {
            throw IllegalArgumentException("Unsupported file type: $filePath")
        }

        val transformedJson = JsonTransformationController.transformJson(jsonString, mapping)
        println(transformedJson)

        println("\nMapeamento de Campos:")
        JsonTransformationController.printFieldMappingTable(mapping)

    } catch (e: Exception) {
        e.printStackTrace()
    }

}
