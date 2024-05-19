
fun main() {
    val filePath = "C:\\Users\\Vitor\\IdeaProjects\\jsonConverter\\src\\main\\resources\\classificação_F.csv"
    val jsonString = """
    {
        "CompanyCode": "1000",
        "PurchaseOrderType": "ZPCR",
        "Supplier": "1000254",
        "PaymentTerms": "NT45",
        "PurchasingOrganization": "MP",
        "PurchasingGroup": "001",
        "PurchaseOrderDate": "2024-05-03",
        "_PurchaseOrderItem": [
            {
                "Material": "197",
                "Plant": "1007",
                "OrderQuantity": 1.000,
                "PurchaseOrderQuantityUnit": "UA",
                "NetPriceAmount": 308848.48,
                "DocumentCurrency": "BRL",
                "TaxCode": "S2",
                "zzpondat": "2024-05-22",
                "zzpoedat": "2024-05-25",
                "zzpoidat": "2024-05-23",
                "zzstatus_in": "4",
                "zzpotptr": 23,
                "AccountAssignmentCategory": "F",
                "MultipleAcctAssgmtDistribution": "2",
                "PartialInvoiceDistribution": "2",
                "GoodsReceiptIsExpected": false,
                "IncotermsClassification": "CIF",
                "IncotermsLocation1": "CIF",
                "_PurOrdAccountAssignment": [
                    {
                        "AccountAssignmentNumber": "1",
                        "MultipleAcctAssgmtDistrPercent": 60.0,
                        "DocumentCurrency": "BRL",
                        "GLAccount": "4010301001",
                        "OrderID": "400025",
                        "BusinessArea": "1001",
                        "ControllingArea": "1000",
                        "FunctionalArea": "33-EST_COM"
                    },
                    {
                        "AccountAssignmentNumber": "2",
                        "MultipleAcctAssgmtDistrPercent": 40.0,
                        "DocumentCurrency": "BRL",
                        "GLAccount": "4010301001",
                        "OrderID": "400025",
                        "BusinessArea": "1001",
                        "ControllingArea": "1000",
                        "FunctionalArea": "33-EST_COM"
                    }
                ],
                "_PurchaseOrderScheduleLineTP": [
                    {
                        "DelivDateCategory": "1",
                        "ScheduleLineOrderQuantity": 1.000,
                        "PurchaseOrderQuantityUnit": "UA",
                        "PerformancePeriodStartDate": "2024-04-08",
                        "PerformancePeriodEndDate": "2024-04-30"
                    }
                ]
            },
            {
                "Material": "197",
                "Plant": "1007",
                "OrderQuantity": 1.000,
                "PurchaseOrderQuantityUnit": "UA",
                "NetPriceAmount": 308848.48,
                "DocumentCurrency": "BRL",
                "TaxCode": "S2",
                "zzpondat": "2024-05-22",
                "zzpoedat": "2024-05-25",
                "zzpoidat": "2024-05-23",
                "zzstatus_in": "4",
                "zzpotptr": 23,
                "AccountAssignmentCategory": "F",
                "MultipleAcctAssgmtDistribution": "2",
                "PartialInvoiceDistribution": "2",
                "GoodsReceiptIsExpected": false,
                "IncotermsClassification": "CIF",
                "IncotermsLocation1": "CIF",
                "_PurOrdAccountAssignment": [
                    {
                        "AccountAssignmentNumber": "1",
                        "MultipleAcctAssgmtDistrPercent": 60.0,
                        "DocumentCurrency": "BRL",
                        "GLAccount": "4010301001",
                        "OrderID": "400025",
                        "BusinessArea": "1001",
                        "ControllingArea": "1000",
                        "FunctionalArea": "33-EST_COM"
                    },
                    {
                        "AccountAssignmentNumber": "2",
                        "MultipleAcctAssgmtDistrPercent": 40.0,
                        "DocumentCurrency": "BRL",
                        "GLAccount": "4010301001",
                        "OrderID": "400025",
                        "BusinessArea": "1001",
                        "ControllingArea": "1000",
                        "FunctionalArea": "33-EST_COM"
                    }
                ],
                "_PurchaseOrderScheduleLineTP": [
                    {
                        "DelivDateCategory": "1",
                        "ScheduleLineOrderQuantity": 1.000,
                        "PurchaseOrderQuantityUnit": "UA",
                        "PerformancePeriodStartDate": "2024-04-08",
                        "PerformancePeriodEndDate": "2024-04-30"
                    }
                ]
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
