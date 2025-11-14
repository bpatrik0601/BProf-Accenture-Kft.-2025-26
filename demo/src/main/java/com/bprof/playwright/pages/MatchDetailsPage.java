package com.bprof.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class MatchDetailsPage {
    private final Page page;

    // Selector constants –-> Separation for better maintainability
    private static final String LOADING_SELECTOR = "p:has-text('Loading match statistics')";
    private static final String TEAM_NAMES_SELECTOR = "h3";
    private static final String SCORE_SELECTOR = "p:has-text('Score')";
    private static final String STATISTICS_SELECTOR = "ul li";

    public MatchDetailsPage(Page page) {
        this.page = page;
    }

    // Locators
    public Locator getLoadingMessage() {
        return page.locator(LOADING_SELECTOR);
    }

    public Locator getTeamNames() {
        return page.locator(TEAM_NAMES_SELECTOR);
    }

    public Locator getScore() {
        return page.locator(SCORE_SELECTOR);
    }

    public Locator getStatisticByLabel(String label) {
        return page.locator(STATISTICS_SELECTOR).filter(new Locator.FilterOptions().setHasText(label));
    }

    // Utility method – value parsing
    private String getStatisticValue(String label) {
        String text = getStatisticByLabel(label).innerText(); // e.g. "Goals: 3 - 1"
        return text.split(":")[1].trim();                     // --> "3 - 1"
    }

    // Specific statistic getters
    public String getGoals() {
        return getStatisticValue("Goals");
    }

    public String getShotsOnTarget() {
        return getStatisticValue("Shots on Target");
    }

    public String getPossession() {
        return getStatisticValue("Possession");
    }

    public String getFouls() {
        return getStatisticValue("Fouls");
    }

    // Extra utility – List all statistics
    public List<String> getAllStatistics() {
        return page.locator(STATISTICS_SELECTOR).allInnerTexts();
    }
}
