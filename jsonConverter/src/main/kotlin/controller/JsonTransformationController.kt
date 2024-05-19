import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.Locale

object JsonTransformationController {

    fun transformJson(jsonString: String, mapping: Map<String, String>): String {
        val objectMapper = ObjectMapper()
        val rootNode = objectMapper.readTree(jsonString)
        val newJsonMap = mutableMapOf<String, JsonNode>()
        transformJsonNode(rootNode, newJsonMap, mapping)
        return objectMapper.writeValueAsString(newJsonMap)
    }

    private fun transformJsonNode(node: JsonNode, newJsonMap: MutableMap<String, JsonNode>, mapping: Map<String, String>) {
        node.fields().forEach { (key, value) ->
            val newKey = mapping[key.trim()] ?: key.lowercase(Locale.getDefault()).replaceFirstChar {
                if (it.isLowerCase()) it else it.lowercaseChar()
            }
            val transformedValue = transformJsonValue(value, mapping)
            newJsonMap[newKey] = transformedValue
        }
    }

    private fun transformJsonValue(node: JsonNode, mapping: Map<String, String>): JsonNode {
        return when {
            node.isObject -> {
                val newJsonMap = mutableMapOf<String, JsonNode>()
                transformJsonNode(node, newJsonMap, mapping)
                ObjectMapper().valueToTree(newJsonMap)
            }
            node.isArray -> {
                val newList = mutableListOf<JsonNode>()
                node.elements().forEach { element ->
                    val transformedElement = transformJsonValue(element, mapping)
                    newList.add(transformedElement)
                }
                ObjectMapper().valueToTree(newList)
            }
            else -> node
        }
    }

    fun printFieldMappingTable(mapping: Map<String, String>) {
        println("+-----------------------------------+-----------------------------------+")
        println("|        Campo Original            |            Campo Transformado     |")
        println("+-----------------------------------+-----------------------------------+")
        mapping.forEach { (original, novo) ->
            println("| ${original.padEnd(33)}| ${novo.padEnd(33)}|") // Ajuste para manter a formatação correta
        }
        println("+-----------------------------------+-----------------------------------+")
    }
}
