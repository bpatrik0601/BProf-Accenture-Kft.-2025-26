package com.bprof.playwright.pages;

import com.bprof.playwright.elements.MatchDetailsElements;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class MatchDetailsPage extends MatchDetailsElements {

    public MatchDetailsPage(Page page) {
        super(page);
    }

    // Wait methods (critical for CI stability)
    public void waitForStatisticsLoaded() {
        page.waitForFunction(".match-details ul li");
    }

    // Basic element getters
    public Locator getLoadingMessage() {
        return loadingMessage;
    }

    public Locator getTeamNames() {
        return teamNames;
    }

    public Locator getScore() {
        return score;
    }

    public Locator getStatisticByLabel(String label) {
        return page.locator("ul li").filter(
            new Locator.FilterOptions().setHasText(label)
        );
    }

    // Utility method – value parsing
    private String getStatisticValue(String label) {
        String text = getStatisticByLabel(label).innerText();  // e.g. "Goals: 3 - 1"
        return text.split(":")[1].trim();             //   --> "3 - 1"
    }

    // Specific statistic getters
    public String getTeamNamesText() {
        return teamNames.innerText();
    }

    public String getScoreText() {
        return score.innerText();
    }

    public boolean isStatisticsVisible() {
        return page.locator(".match-details ul li").first().isVisible();
    }

    public String getGoals() {
        return getStatisticValue("Goals");
    }

    public String getShotsOnTarget() {
        return getStatisticValue("Shots on Target");
    }

    public String getPossession() {
        return getStatisticValue("Possession");
    }

    public String getCorners() {
        return getStatisticValue("Corners");
    }

    public String getFouls() {
        return getStatisticValue("Fouls");
    }

    // Extra utility – List all statistics
    public List<String> getAllStatistics() {
        return page.locator(".match-details ul li").allInnerTexts();
    }
}
