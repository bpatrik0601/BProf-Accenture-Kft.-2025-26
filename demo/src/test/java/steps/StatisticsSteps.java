package steps;

import io.cucumber.java.en.*;
import org.json.JSONObject;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

public class StatisticsSteps {
    JSONObject statistics;
    JSONObject selectedMatch;

    @Given("the statistics data exists")
    public void statisticsDataExists() throws Exception {
        // Precondition: statistics data file is available (in resources/data_JSON_for_offline_fallback/ folder)
        Path path = Paths.get("src/test/resources/data_JSON_for_offline_fallback/match-statistics.json");
        assertTrue(Files.exists(path), "Statistics data file should exist");
    }

    @When("the system loads matches' statistics {string}")
    public void loadStatisticsFile(String fileName) throws Exception {
        String path = "src/test/resources/data_JSON_for_offline_fallback/" + fileName;
        String json = Files.readString(Paths.get(path));
        statistics = new JSONObject(json);
    }

    @And("the user selects match {string}")
    public void selectMatch(String matchId) {
        selectedMatch = statistics.getJSONObject(matchId);
    }

    @Then("the statistics should be shown on screen")
    public void showStatistics() {
        assertNotNull(selectedMatch);
        System.out.println("Goals: " + selectedMatch.getJSONObject("goals").getInt("home")
                + " - " + selectedMatch.getJSONObject("goals").getInt("away"));
        System.out.println("Shots on target: " + selectedMatch.getJSONObject("shotsOnTarget").getInt("home")
                + " - " + selectedMatch.getJSONObject("shotsOnTarget").getInt("away"));
        System.out.println("Possession: " + selectedMatch.getJSONObject("possession").getInt("home")
                + "% - " + selectedMatch.getJSONObject("possession").getInt("away") + "%");
        System.out.println("Fouls: " + selectedMatch.getJSONObject("fouls").getInt("home")
                + " - " + selectedMatch.getJSONObject("fouls").getInt("away"));
    }
}
