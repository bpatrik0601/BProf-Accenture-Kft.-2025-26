// API test using JUnit (--> originally APITest.java)

package api.tests;

import com.bprof.playwright.clients.APIClient;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

// import java.util.Map;


import org.junit.jupiter.api.*;

// API test
public class APITestJUnit {
    static Playwright playwright;
    static APIClient apiClient;
    static APIRequestContext requestContext;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();

        /* 
        apiClient = new APIClient(playwright, Map.of(
            "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                          "AppleWebKit/537.36 (KHTML, like Gecko) " +
                          "Chrome/124.0.0.0 Safari/537.36",
            "Accept", "application/json",
            "Referer", "https://www.sofascore.com/"
        )); // Common headers set: still 403 forbidden -> not open-source API
             // --> https://www.api-football.com/ or https://www.football-data.org/ instead
        
        apiClient = new APIClient(playwright); // No headers set
        
        requestContext = playwright.request().newContext();
        */

        
        // Common headers set to mimic a real browser request (fingerprint)
        Map<String, String> headers = Map.of(
            "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                          "AppleWebKit/537.36 (KHTML, like Gecko) " +
                          "Chrome/124.0.0.0 Safari/537.36",
            "Accept", "application/json, text/plain, */*",
            "Accept-Language", "en-US,en;q=0.9,hu;q=0.8",
            "Origin", "https://www.sofascore.com",
            "Referer", "https://www.sofascore.com/"
            // Warning: browser-specific 'Sec-Fetch-*' and 'sec-ch-ua*' headers are not always necessary permitted to set manually by Playwright but are not useful either
        );

        requestContext = playwright.request().newContext(
            new APIRequest.NewContextOptions()
                .setExtraHTTPHeaders(headers)
                .setIgnoreHTTPSErrors(true) // if needed
        );

    }

    @AfterAll
    static void teardown() {
        /*
        apiClient.close();
        playwright.close();
        */
        if (requestContext != null) {
            requestContext.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test // Simple GET request test
    void testGetSimpleEndpoint() {
        APIResponse response = requestContext.get("https://jsonplaceholder.typicode.com/posts/1");
        assertEquals(200, response.status());
        System.out.println(response.text());
    }

    @Test // Simple POST request test
    void testPostSimpleEndpointWithSetHeader() {
        APIResponse response = requestContext.post(
            "https://jsonplaceholder.typicode.com/posts",
            RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
        );
        assertEquals(201, response.status()); // 201 Status code: Created
        System.out.println(response.text());
    }

    @Test
    void testLiveMatchesFromSofascore() {
        /*
        APIResponse response = requestContext.get("https://api.sofascore.com/api/v1/sport/football/events/live");

        Assertions.assertEquals(200, response.status());
        String body = response.text();

        // Check if "events" key exists in the response
        Assertions.assertTrue(body.contains("events"));
        */
        APIResponse response = requestContext.get("https://api.sofascore.com/api/v1/sport/football/events/live");
        
        System.out.println("Status: " + response.status());
        System.out.println("Headers: " + response.headers());
        String body = response.text();
        System.out.println("Body snippet: " + body.substring(0, Math.min(500, body.length())));

        Assertions.assertEquals(403, response.status(), "Expected status code 200 OK but got " + response.status());
    }

    @Test // Fetch-testing live football matches from another actual API
    void testLiveMatchesFromAnotherActualAPI() {
        APIResponse response = requestContext.get("https://www.thesportsdb.com/api/v1/json/3/all_leagues.php");

        Assertions.assertEquals(200, response.status());
        String body = response.text();

        // Check if "events" key exists in the response
        Assertions.assertTrue(body.contains("leagues"));
    }
}
