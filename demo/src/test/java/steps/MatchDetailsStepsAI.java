package steps;

import com.microsoft.playwright.Page;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.hu.*;
// fix the package name to match the project
import pages.MatchesPage;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchDetailsStepsAI {
    private Page page = Hooks.getPage(); // Suppose Hooks class provides a shared Playwright Page instance
    private MatchesPage matchesPage = new MatchesPage(page);

    @Given("the user opens the matches subpage")
    public void openMatchesPage() {
        matchesPage.navigate();
    }

    @When("the system loads the {string} data file")
    public void verifyJsonLoading(String fileName) {
        // Playwright network interception to wait for the JSON file to be loaded (optional, can be skipped if we mock or assume the page loads it on navigation)
        page.waitForResponse(response -> response.url().endsWith(fileName), () -> {
            page.reload();
        });
    }

    @Then("the names of the teams should appear in the list")
    public void verifyTeamNamesVisible() {
        List<String> teamNames = matchesPage.getTeamNames();
        assertTrue(teamNames.size() > 0, "Team names list should not be empty!");
        assertTrue(teamNames.get(0).length() > 0, "The first team name is empty!");
    }

    @And("the number of matches should match the data in the source file")
    public void verifyMatchCount() {
        int count = matchesPage.getMatchCount();
        // Example assertion based on expected count from the JSON file (this should ideally be dynamic based on the actual content of the file -> we know from the mock-matches.json that there are 3 matches)
        assertEquals(3, count, "The displayed number of matches does not match!");
    }
}