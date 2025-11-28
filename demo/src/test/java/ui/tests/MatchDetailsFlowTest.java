package ui.tests;

import com.microsoft.playwright.*;

import java.util.List;

import org.junit.jupiter.api.*;

import com.bprof.playwright.pages.MatchDetailsPage;

public class MatchDetailsFlowTest { // loading message, team names, score, statistics
    static Playwright playwright;
    static Browser browser;
    Page page;
    MatchDetailsPage details;

    @BeforeAll
    static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void setup() {
        page = browser.newPage();
        page.navigate("http://localhost:4200/match/1001");
        details = new MatchDetailsPage(page);
    }

    @Test
    void testStatisticsVisibleAfterLoad() {                          // alternative option: testing whole process of loading and not only final state -> void testLoadingMessageVisibleInitially()
        page.navigate("http://localhost:4200/match/1001");
        page.waitForSelector(".match-details");            // artificial delay of mock API in the app (Angular service -> this.http.get('/assets/mock/match-statistics.json').pipe(delay(2000)).subscribe((statsData: any) => { ... });
        Assertions.assertTrue(details.getTeamNames().isVisible());  //  Assertions.assertTrue(details.getTeamNames().isVisible());
    }

    @Test
    void testTeamNamesAndDateDisplayed() {
        page.navigate("http://localhost:4200/match/1001");
        page.waitForSelector(".match-details");
        Assertions.assertTrue(details.getTeamNames().isVisible());
        Assertions.assertTrue(page.locator(".match-date").isVisible());
    }

    @Test
    void testScoreDisplayedCorrectly() {
        page.navigate("http://localhost:4200/match/1001");
        page.waitForSelector(".score");
        String scoreText = details.getScore().getText();
        Assertions.assertEquals("Score: 3 - 1", scoreText);
    }

    @Test
    void testGoalsStatistic() {
        page.navigate("http://localhost:4200/match/1001");
        page.waitForSelector(".statistics-title");
        String goals = details.getGoals();
        Assertions.assertEquals("3 - 1", goals);
    }

    @Test
    void testShotsOnTargetStatistic() {
        String shots = details.getShotsOnTarget();
        Assertions.assertEquals("17 - 6", shots);
    }

    @Test
    void testPossessionStatistic() {
        String possession = details.getPossession();
        Assertions.assertEquals("68% - 32%", possession);
    }

    @Test
    void testFoulsStatistic() {
        String fouls = details.getFouls();
        Assertions.assertEquals("8 - 14", fouls);
    }

    @Test
    void testAllStatisticsListed() {
        List<String> stats = details.getAllStatistics();
        Assertions.assertTrue(stats.contains("Goals: 3 - 1"));
        Assertions.assertTrue(stats.contains("Shots on Target: 17 - 6"));
        Assertions.assertTrue(stats.contains("Possession: 68% - 32%"));
        Assertions.assertTrue(stats.contains("Fouls: 8 - 14"));
    }

    @AfterEach
    void tearDown() {
        page.close();
    }

    @AfterAll
    static void tearDownClass() {
        browser.close();
        playwright.close();
    }
}
