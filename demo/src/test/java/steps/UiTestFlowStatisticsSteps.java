package steps;

import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.microsoft.playwright.*; // import Playwright; Page; Browser; BrowserType.

import com.bprof.playwright.pages.MatchDetailsPage;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
/* 
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
*/
import io.cucumber.java.en.*;

public class UiTestFlowStatisticsSteps {
    private static Playwright playwright;
    private static Browser browser;
    private Page page;
    private MatchDetailsPage details;

    @BeforeAll
    public static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }


    @Given("I open the match details for {string}")
    public void openMatchDetails(String matchId) {
        page = browser.newPage();
        page.navigate("http://localhost:4200/match/" + matchId);
        details = new MatchDetailsPage(page);
    }

    @When("the page is loaded")
    public void waitForDetails() {
        page.waitForSelector(".match-details");
    }

    @Then("the team names should be visible")
    public void checkTeamNamesVisible() {
        Assertions.assertTrue(details.getTeamNames().isVisible());
    }

    @Then("the match date should be visible")
    public void checkMatchDateVisible() {
        Assertions.assertTrue(page.locator(".match-date").isVisible());
    }

    @Then("the score should be {string}")
    public void checkScore(String expected) {
        Assertions.assertEquals(expected, details.getScore().innerText());
    }

    @Then("the goals statistic should be {string}")
    public void checkGoals(String expected) {
        Assertions.assertEquals(expected, details.getGoals());
    }

    @Then("the shots on target statistic should be {string}")
    public void checkShots(String expected) {
        Assertions.assertEquals(expected, details.getShotsOnTarget());
    }

    @Then("the possession statistic should be {string}")
    public void checkPossession(String expected) {
        Assertions.assertEquals(expected, details.getPossession());
    }

    @Then("the fouls statistic should be {string}")
    public void checkFouls(String expected) {
        Assertions.assertEquals(expected, details.getFouls());
    }

    @Then("the statistics should include:")
    public void checkAllStatistics(io.cucumber.datatable.DataTable dataTable) {
        List<String> expectedStats = dataTable.asList();
        List<String> actualStats = details.getAllStatistics();
        Assertions.assertTrue(actualStats.containsAll(expectedStats));
    }


    @AfterAll
    public static void tearDownClass() {
        browser.close();
        playwright.close();
    }
}
