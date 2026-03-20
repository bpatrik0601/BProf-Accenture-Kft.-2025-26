package steps;

public class MatchDetailsStepsAI {
    
}

package stepdefinitions;

import com.microsoft.playwright.Page;
import io.cucumber.java.hu.*;
import pages.MatchesPage;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchSteps {
    private Page page = Hooks.getPage(); // Feltételezve egy Hooks osztályt
    private MatchesPage matchesPage = new MatchesPage(page);

    @Adott("a felhasználó megnyitja a meccsek aloldalt")
    public void openMatchesPage() {
        matchesPage.navigate();
    }

    @Amikor("a rendszer betölti a {string} adatfájlt")
    public void verifyJsonLoading(String fileName) {
        // Playwright hálózati figyelés (opcionális, ha mockolunk)
        page.waitForResponse(response -> response.url().endsWith(fileName), () -> {
            page.reload();
        });
    }

    @Akkor("a listában meg kell jelenniük a csapatok neveinek")
    public void verifyTeamNamesVisible() {
        List<String> teamNames = matchesPage.getTeamNames();
        assertTrue(teamNames.size() > 0, "A csapatnevek listája üres!");
        assertTrue(teamNames.get(0).length() > 0, "Az első csapatnév üres!");
    }

    @És("a meccsek számának egyeznie kell a forrásfájlban lévő adatokkal")
    public void verifyMatchCount() {
        int count = matchesPage.getMatchCount();
        // Példa: ha tudjuk, hogy a mock-ban 3 meccs van
        assertEquals(3, count, "A megjelenített meccsek száma nem egyezik!");
    }
}