package tests;

import com.microsoft.playwright.*;

import java.util.Map;

import org.junit.jupiter.api.*;

// API test
public class APITest {
    static Playwright playwright;
    static APIRequestContext requestContext;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        requestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
        .setExtraHTTPHeaders(Map.of(
            "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                          "AppleWebKit/537.36 (KHTML, like Gecko) " +
                          "Chrome/124.0.0.0 Safari/537.36",
            "Accept", "application/json",
            "Referer", "https://www.sofascore.com/"
        ))); // Common headers set: still 403 forbidden -> not open-source API
             // --> https://www.api-football.com/ or https://www.football-data.org/ instead
    }

    @AfterAll
    static void teardown() {
        requestContext.dispose();
        playwright.close();
    }

    @Test
    void testLiveMatchesFromSofascore() {
        APIResponse response = requestContext.get("https://api.sofascore.com/api/v1/sport/football/events/live");

        Assertions.assertEquals(200, response.status());
        String body = response.text();

        // Check if "events" key exists in the response
        Assertions.assertTrue(body.contains("events"));
    }
}
