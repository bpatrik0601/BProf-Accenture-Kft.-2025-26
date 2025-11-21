package ui.tests;

import com.bprof.playwright.pages.MatchPageTest;
import com.microsoft.playwright.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

// UI test
public class MatchTest {
    static Playwright playwright;
    static Browser browser;
    Page page;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void createPage() {
        page = browser.newPage();
    }

    @Test
    void testMatchPageTitle() {
        MatchPageTest matchPage = new MatchPageTest(page);
        matchPage.goTo();
        String title = matchPage.getTitle();
        assertTrue(title.contains("Sofascore"));
    }

    @AfterAll
    static void teardown() {
        browser.close();
        playwright.close();
    }
}
