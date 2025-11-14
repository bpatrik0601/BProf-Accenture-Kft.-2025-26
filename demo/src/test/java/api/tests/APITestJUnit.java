package api.tests;

import com.bprof.playwright.clients.APIClient;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* 
import java.util.Map;
*/

import org.junit.jupiter.api.*;

// API test
public class APITestJUnit {
    static Playwright playwright;
    static APIClient apiClient;

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
        */

        apiClient = new APIClient(playwright); // No headers set
    }

    @AfterAll
    static void teardown() {
        apiClient.close();
        playwright.close();
    }

    @Test // Simple GET request test
    void testGetSimpleEndpoint() {
        APIResponse response = apiClient.get("https://jsonplaceholder.typicode.com/posts/1");
        assertEquals(200, response.status());
        System.out.println(response.text());
    }

    @Test // Simple POST request test
    void testPostSimpleEndpointWithSetHeader() {
        APIResponse response = apiClient.post(
            "https://jsonplaceholder.typicode.com/posts",
            RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
        );
        assertEquals(201, response.status()); // 201 Status code: Created
        System.out.println(response.text());
    }

    @Test // Fetch-testing live football matches from actual API
    void testLiveMatchesFromActualAPI() {
        APIResponse response = apiClient.get("https://www.thesportsdb.com/api/v1/json/3/all_leagues.php");

        Assertions.assertEquals(200, response.status());
        String body = response.text();

        // Check if "events" key exists in the response
        Assertions.assertTrue(body.contains("leagues"));
    }

    @Test // Actual project-related API test from Angular app --> dashboard site / matches.json file 
    void testFetchProjectMatches() {
        APIResponse response = apiClient.get("http://localhost:4200/assets/mock/matches.json");

        Assertions.assertEquals(200, response.status());
        
        String body = response.text();
        
        // Check if "events" key exists in the response and 'matches' isn't present
        Assertions.assertTrue(body.contains("events"));
        Assertions.assertFalse(body.contains("matches"));
   
        // Further checks whether certain parameters exist in the response
        Assertions.assertTrue(body.contains("id"));
        Assertions.assertFalse(body.contains("\"id\":50"));
        Assertions.assertTrue(body.contains("homeTeam"));
    }


    @Test // Test to fetch a specific match by ID from the matches.json file
    void testFetchSpecificMatchById() {
        APIResponse response = apiClient.get("http://localhost:4200/assets/mock/match-statistics.json");

        Assertions.assertEquals(200, response.status());
        
        String body = response.text();

        // Check if a specific match ID exists in the response
        Assertions.assertTrue(body.contains("1092"));
        Assertions.assertFalse(body.contains("22"));
    }
}
