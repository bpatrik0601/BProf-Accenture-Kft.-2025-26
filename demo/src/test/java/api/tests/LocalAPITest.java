package api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalAPITest {

    @Test
    void parseSimpleJson() throws Exception {
        String json_example = "{ \"team\": \"Barcelona\", \"goals\": 3 }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json_example);

        assertEquals("Barcelona", node.get("team").asText());
        assertEquals(3, node.get("goals").asInt());
    }
}