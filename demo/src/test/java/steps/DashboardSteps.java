package steps;

import io.cucumber.java.en.*;
import org.json.JSONObject;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

public class DashboardSteps {
    JSONObject matchData;

    @Given("the dashboard is opened")
    public void openDashboard() {
        System.out.println("Dashboard initialized");
    }

    @When("the system loads {string}")
    public void loadMatchData(String fileName) throws Exception {
        String json = Files.readString(Paths.get("src/test/resources/data/" + fileName));
        matchData = new JSONObject(json);
    }

    @Then("the home and away teams should be shown")
    public void verifyTeamsDisplayed() {
        assertNotNull(matchData.getString("homeTeam"));
        assertNotNull(matchData.getString("awayTeam"));
        System.out.println("Teams: " + matchData.getString("homeTeam") + " vs " + matchData.getString("awayTeam"));
    }
}
