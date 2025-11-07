package api;

import com.bprof.playwright.clients.APIClient;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.*;

// API test
public class APITest {
    static Playwright playwright;
    static APIClient apiClient;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        apiClient = new APIClient(playwright, Map.of(
            "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                          "AppleWebKit/537.36 (KHTML, like Gecko) " +
                          "Chrome/124.0.0.0 Safari/537.36",
            "Accept", "application/json",
            "Referer", "https://www.sofascore.com/"
        )); // Common headers set: still 403 forbidden -> not open-source API
             // --> https://www.api-football.com/ or https://www.football-data.org/ instead
    }

    @AfterAll
    static void teardown() {
        apiClient.close();
        playwright.close();
    }

    @Test // Simple GET request test
    void testGetEndpoint() {
        APIResponse response = apiClient.get("https://jsonplaceholder.typicode.com/posts/1");
        assertEquals(200, response.status());
        System.out.println(response.text());
    }

    @Test // Simple POST request test
    void testPostEndpoint() {
        APIResponse response = apiClient.post(
            "https://jsonplaceholder.typicode.com/posts",
            RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
        );
        assertEquals(201, response.status()); // 201 Status code: Created
        System.out.println(response.text());
    }

    @Test // Test to fetch live football matches from actual API
    void testLiveMatchesFromActualAPI() {
        APIResponse response = apiClient.get("https://www.thesportsdb.com/api/v1/json/3/all_leagues.php");

        Assertions.assertEquals(200, response.status());
        String body = response.text();

        // Check if "events" key exists in the response
        Assertions.assertTrue(body.contains("leagues"));
    }
}
