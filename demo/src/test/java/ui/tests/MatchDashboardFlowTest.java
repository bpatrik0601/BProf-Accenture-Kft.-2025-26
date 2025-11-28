package ui.tests;

import com.microsoft.playwright.*;
// import com.microsoft.playwright.options.AriaRole; --> used for an alternative way to select headings

import java.util.List;

import org.junit.jupiter.api.*;

import com.bprof.playwright.pages.MatchDashboardPage;

public class MatchDashboardFlowTest { // status message, number of match cards, click navigation
    static Playwright playwright;
    static Browser browser;
    Page page;
    MatchDashboardPage dashboard;

    @BeforeAll
    static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void setup() {
        page = browser.newPage();
        page.navigate("http://localhost:4200/");
        dashboard = new MatchDashboardPage(page);
    }

    @Test
    void testStatusMessageChangesAfterLoad() {
        // Initial status message "Loading matches..."
        Assertions.assertEquals("Loading matches...", dashboard.getStatusMessage().innerText());

        // If match cards are loaded, status message should change and appear as "Matches loaded"
        dashboard.waitForMatches(); // replaces page.waitForSelector(".match-card");
        Assertions.assertEquals("Matches loaded", dashboard.getStatusMessage().innerText());
    }

    @Test
    void testLeagueHeadersDisplayed() {
        dashboard.waitForMatches();

        // League headers text list for easier assertions
        List<String> texts = dashboard.getLeagueHeadersText();
        
        Assertions.assertTrue(texts.contains("La Liga"));
        Assertions.assertTrue(texts.contains("Premier League"));
        Assertions.assertTrue(texts.contains("Champions League"));
        Assertions.assertFalse(texts.contains("League 1"));
    }

    @Test // Basic smoke test for match count
    void testMatchCountGreaterThanZero() {
        dashboard.waitForMatches();
        Assertions.assertTrue(dashboard.getMatchCount() > 0);
    }

    @Test
    void testMatchCountMatchesJsonData() {
        dashboard.waitForMatches();
        int uiCount = dashboard.getMatchCount();
        // Based on the mock JSON data file (mock-matches.json) used in the demo app
        Assertions.assertEquals(8, uiCount);
    }

    @Test
    void testMatchCountGroupedByLeague() {
        page.waitForSelector(".match-card");

        Locator laLigaHeader = page.locator(".match-list h3").filter(
            new Locator.FilterOptions().setHasText("La Liga")                       // alternative option: AriaRole.HEADING --> Locator laLigaHeader = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("La Liga").setLevel(3));
        );
        Locator leagueContainer = laLigaHeader.locator("xpath=..");       // parent <div *ngFor="let group ...">
        int laLigaCount = leagueContainer.locator(".match-card").count();

        Assertions.assertEquals(3, laLigaCount);
    }

    @Test
    void testMatchCardContent() {
        Locator card = page.locator(".match-card").first();
        String text = card.innerText();
        Assertions.assertTrue(text.contains("Borussia Dortmund"));
        Assertions.assertTrue(text.contains("RB Leipzig"));
        Assertions.assertTrue(text.contains("4 - 0"));
    }

    @Test
    void testClickMatchNavigatesToDetails() {
        dashboard.clickMatchById("1001"); // Barcelona vs Valencia
        Assertions.assertTrue(page.url().contains("/match/1001"));
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
