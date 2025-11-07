package steps;

import com.bprof.playwright.clients.APIClient;
import com.microsoft.playwright.*;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApiSteps {
    static Playwright playwright;
    static APIClient apiClient;
    APIResponse response;

    @Given("the API client is initialized")
    public void initApiClient() {
        playwright = Playwright.create();
        apiClient = new APIClient(playwright);
    }

    @When("I send a GET request to {string}")
    public void sendGetRequest(String url) {
        response = apiClient.get(url);
    }

    @When("I send a POST request to {string} with body:")
    public void sendPostRequest(String url, String body) {
        response = apiClient.post(url, com.microsoft.playwright.options.RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(body));
    }

    @Then("the response status should be {int}")
    public void checkStatus(int status) {
        assertEquals(status, response.status());
    }

    @Then("the response body should contain {string}")
    public void checkBodyContains(String text) {
        assertTrue(response.text().contains(text));
    }
}