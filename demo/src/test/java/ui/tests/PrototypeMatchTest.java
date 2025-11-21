/* 
First, initial / prototype version UI test for a football match page using Playwright in Java.
Will be later removed from the project.
*/

package ui.tests;

import com.bprof.playwright.pages.PrototypeMatchPage;
import com.microsoft.playwright.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

// UI test
public class PrototypeMatchTest {
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
        PrototypeMatchPage matchPage = new PrototypeMatchPage(page);
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
