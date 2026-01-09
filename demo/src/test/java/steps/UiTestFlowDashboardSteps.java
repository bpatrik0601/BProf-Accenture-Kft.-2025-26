package steps;

import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.microsoft.playwright.*; // import Playwright; Page; Browser; BrowserType.

import com.bprof.playwright.pages.MatchDashboardPage;

/* 
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
*/
import io.cucumber.java.en.*;

import hooks.Hooks;

public class UiTestFlowDashboardSteps {
    private Page page;
    private MatchDashboardPage dashboard;


    @Given("I open the match dashboard")
    public void openDashboard() {
        page = Hooks.getPage();
        dashboard = new MatchDashboardPage(page);
        dashboard.open();
    }

    @Then("I should see the status message {string}")
    public void loadingState(String expected) {
        dashboard.waitForStatusMessage(expected);
    }

    @When("the matches are loaded")
    public void waitForMatches() {
        dashboard.waitForMatches();
    }

    @Then("I should see the following league headers:")
    public void checkLeagueHeaders(io.cucumber.datatable.DataTable dataTable) {
        List<String> expectedHeaders = dataTable.asList();
        List<String> actualHeaders = dashboard.getLeagueHeadersText();
        Assertions.assertTrue(actualHeaders.containsAll(expectedHeaders));
    }

    @And("I should not see {string}")
    public void notToBeSeen(String text) {
        List<String> actualHeaders = dashboard.getLeagueHeadersText();
        Assertions.assertFalse(actualHeaders.contains(text),
            "Unexpected league header: " + text);
    }

    @Then("the match count should be {int}")
    public void checkMatchCount(int expected) {
        Assertions.assertEquals(expected, dashboard.getMatchCount());
    }

    @Then("the match count should be greater than {int}")
    public void checkMatchCountGreater(int min) {
        Assertions.assertTrue(dashboard.getMatchCount() > min);
    }

    @Then("La Liga should have {int} matches")
    public void laLigaShouldHaveMatches(int expectedCount) {
        // Finding parent locator (div) for La Liga section
        Locator laLigaSection = page.locator(".match-list div:has(h3:has-text('La Liga'))");

        // Counting the match cards within that section
        int actualCount = laLigaSection.locator(".match-card").count();

        Assertions.assertEquals(expectedCount, actualCount,
            "La Liga matches's count does not check up with expected: " + expectedCount);
    }

    @Then("the first match card should contain:")
    public void theFirstMatchCardShouldContain(io.cucumber.datatable.DataTable dataTable) {
        // First match card text
        String firstCardText = dashboard.getAllMatchCards().get(0).innerText();

        // DataTable rows compared against the first match card text
        List<String> expectedLines = dataTable.asList();
        for (String expected : expectedLines) {
            Assertions.assertTrue(firstCardText.contains(expected),
                "The first match-card doesn't contain: " + expected);
        }
    }

    @When("I click the match with id {string}")
    public void clickMatchById(String matchId) {
        dashboard.clickMatchById(matchId);
    }

    @Then("I should be navigated to {string}")
    public void checkNavigation(String expectedUrlPart) {
        Assertions.assertTrue(page.url().contains(expectedUrlPart));
    }

}
