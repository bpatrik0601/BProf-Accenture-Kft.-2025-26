package steps;

import io.cucumber.java.en.*;
import org.json.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorSteps {
    private Exception error;

    @Given("the dashboard is opened")
    public void openDashboard() {
        System.out.println("Dashboard opened for error test");
    }

    @When("the system loads {string}")
    public void loadInvalidJson(String fileName) {
        try {
            String content = Files.readString(Paths.get("src/test/resources/data/" + fileName));
            new JSONObject(content); // parse JSON
        } catch (Exception e) {
            error = e;
        }
    }

    @Then("an error message should appear")
    public void errorShouldAppear() {
        assertNotNull(error, "An error should have occurred due to invalid JSON");
        System.out.println("Error caught successfully: " + error.getMessage());
    }
}