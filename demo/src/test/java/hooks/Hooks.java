package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import com.microsoft.playwright.*;

public class Hooks {
    private static Browser browser;
    private static Page page; // make page accessible via static getPage() OR an other option could be dependency injection with PicoContainer (SharedContext class)
    private static Playwright playwright;

    @Before
    public void setUp() {
        playwright = Playwright.create();
        browser = Playwright.create().chromium().launch();
        page = browser.newPage();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) { // Capture screenshot on failure (void takeScreenshotOnFailure method)
            byte[] screenshot = page.screenshot(
                new Page.ScreenshotOptions().setFullPage(true) // fundamentally no screenshot function --> has to be implemented: true -> Capture the full page <> false -> only the visible viewport
            );
            scenario.attach(screenshot, "image/png", "Error Screenshot");
        }
        browser.close();
        playwright.close();
    }

    public static Page getPage() {
        return page;
    }
}
