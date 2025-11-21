package com.bprof.playwright.pages;

import com.bprof.playwright.elements.Element;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class MatchDetailsPage {
    private final Page page;

    // Selector constants – Separation for better maintainability
    private static final String LOADING_SELECTOR = "p:has-text('Loading match statistics')";
    private static final String TEAM_NAMES_SELECTOR = "h3";
    private static final String SCORE_SELECTOR = "p:has-text('Score')";
    private static final String STATISTICS_SELECTOR = "ul li";

    public MatchDetailsPage(Page page) {
        this.page = page;
    }

    // Elements
    public Element getLoadingMessage() {
        return new Element(page.locator(LOADING_SELECTOR));
    }

    public Element getTeamNames() {
        return new Element(page.locator(TEAM_NAMES_SELECTOR));
    }

    public Element getScore() {
        return new Element(page.locator(SCORE_SELECTOR));
    }

    public Element getStatisticByLabel(String label) {
        Locator locator = page.locator(STATISTICS_SELECTOR).filter(new Locator.FilterOptions().setHasText(label));
        return new Element(locator);
    }

    // Utility method – value parsing
    private String getStatisticValue(String label) {
        String text = getStatisticByLabel(label).getText();  // e.g. "Goals: 3 - 1"
        return text.split(":")[1].trim();             //   --> "3 - 1"
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
