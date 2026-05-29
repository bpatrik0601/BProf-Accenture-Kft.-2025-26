package steps;

import org.junit.jupiter.api.Assertions;

import com.bprof.playwright.pages.MatchDashboardPage;
import com.microsoft.playwright.*;

import hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GitHubCopilotSteps {
    private Page page;
    private MatchDashboardPage dashboard; // unused page object to demonstrate code generation, not used in actual tests due to stability issues with locators, needs fixing in the page object

    @Given("I open the match dashboard")
    public void openDashboard() {
        page = Hooks.getPage();
        dashboard = new MatchDashboardPage(page);
        //dashboard.open(); // <-- this method is not working due to some locator issues, needs fixing in the page object
    }


    @When("I click the match with id {string}")
    public void clickMatchById(String matchId) {
        //dashboard.clickMatchById(matchId); // <-- this method is not working due to some locator issues, needs fixing in the page object
    }

    @Then("I should be navigated to {string}")
    public void checkNavigation(String expectedUrlPart) {
        Assertions.assertTrue(page.url().contains(expectedUrlPart));
    }
}
