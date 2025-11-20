package steps;

import io.cucumber.java.en.*;
import org.json.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorSteps {
    private Exception error;

    @Given("the invalid match data exists")
    public void invalidMatchDataExists() throws Exception {
        // Precondition: invalid match data file is available (in resources/data_JSON_for_offline_fallback/ folder)
        Path path = Paths.get("src/test/resources/data_JSON_for_offline_fallback/invalid-matches.json");
        assertTrue(Files.exists(path), "Invalid match data file should exist");
    }

    @When("the system loads the invalid file {string}") // data-quality-focused errors instead of syntax errors
    public void loadInvalidJson(String fileName) {
        try {
            String content = Files.readString(
                Paths.get("src/test/resources/data_JSON_for_offline_fallback/" + fileName)
            );
            JSONObject obj = new JSONObject(content);
            JSONArray events = obj.getJSONArray("events");

            for (int i = 0; i < events.length(); i++) {
                JSONObject event = events.getJSONObject(i);

                // Example validation: id must be positive
                if (event.getInt("id") <= 0 || event.getInt("id") > 100000) {
                    throw new IllegalArgumentException("Invalid id: must between 1 and 100000");
                }

                // Example validation: scores must be integers
                if (!(event.get("homeScore") instanceof Integer) ||
                    !(event.get("awayScore") instanceof Integer)) {
                    throw new IllegalArgumentException("Scores must be integers");
                }
            }
        } catch (Exception e) {
            error = e; // Capture any error that occurs during parsing/validation
        }
    }


    @Then("an error message should appear")
    public void errorShouldAppear() {
        assertNotNull(error, "An error should have occurred due to invalid JSON");
        System.out.println("Error caught successfully: " + error.getMessage());
    }
}