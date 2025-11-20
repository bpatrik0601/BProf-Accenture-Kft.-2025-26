package steps;

import io.cucumber.java.en.*;
import org.json.JSONObject;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

public class StaticDataHandlingSteps { // JSON data handling steps
    JSONObject matchData;

    @Given("the match data exists")
    public void matchDataExists() throws Exception {
        // Precondition: match data file is available (in resources/data_JSON_for_offline_fallback/ folder)
        Path path = Paths.get("src/test/resources/data_JSON_for_offline_fallback/matches.json");
        assertTrue(Files.exists(path), "Match data file should exist");
    }

    @When("the system loads matches' data {string}")
    public void loadMatchData(String fileName) throws Exception {
        String json = Files.readString(Paths.get("src/test/resources/data_JSON_for_offline_fallback/" + fileName));
        matchData = new JSONObject(json); // parse JSON root object
    }

    @Then("the home and away teams should be shown")
    public void verifyTeamsDisplayed() {
        // "events" array contains match objects from where we can get team names
        var events = matchData.getJSONArray("events");

        for (int i = 0; i < events.length(); i++) {
            var matchData = events.getJSONObject(i);
            
            String homeTeam = matchData.getString("homeTeam");
            String awayTeam = matchData.getString("awayTeam");
            
            assertNotNull(homeTeam, "Home team should not be null but presented");
            assertNotNull(awayTeam, "Away team should not be null but presented");
            
            System.out.println("Teams: " + matchData.getString("homeTeam") + " vs " + matchData.getString("awayTeam"));
        }
    }
}
