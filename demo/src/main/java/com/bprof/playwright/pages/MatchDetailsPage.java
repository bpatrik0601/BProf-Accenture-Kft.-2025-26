package com.bprof.playwright.pages;

import com.bprof.playwright.wrappers.GeneralElementWrapper;
import com.bprof.playwright.elements.MatchDetailsElements;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class MatchDetailsPage extends MatchDetailsElements {

    public MatchDetailsPage(Page page) {
        super(page);
    }

    // Elements - inherited from MatchDetailsElements
    public GeneralElementWrapper getLoadingMessage() {
        return loadingMessage;
    }

    public GeneralElementWrapper getTeamNames() {
        return teamNames;
    }

    public GeneralElementWrapper getScore() {
        return score;
    }

    public GeneralElementWrapper getStatistics() {
        return statistics;
    }

    public GeneralElementWrapper getStatisticByLabel(String label) {
        Locator locator = statistics.getLocator()
            .filter(new Locator.FilterOptions().setHasText(label));
        return new GeneralElementWrapper(locator);
    }

    // Utility method – value parsing
    private String getStatisticValue(String label) {
        String text = getStatisticByLabel(label).getText();  // e.g. "Goals: 3 - 1"
        return text.split(":")[1].trim();             //   --> "3 - 1"
    }

    // Specific statistic getters
    public String getTeamNamesText() {
        return teamNames.getText();
    }

    public String getScoreText() {
        return score.getText();
    }

    public boolean isStatisticsVisible() {
        return statistics.isVisible();
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
        return statistics.getLocator().allInnerTexts();
    }
}
