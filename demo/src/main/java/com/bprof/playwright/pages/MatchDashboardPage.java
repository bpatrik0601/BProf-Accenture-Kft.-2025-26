package com.bprof.playwright.pages;

import com.bprof.playwright.elements.MatchDashboardElements;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.List;

public class MatchDashboardPage extends MatchDashboardElements {

    public MatchDashboardPage(Page page) {
        super(page);
    }

    // Wait methods (critical for CI stability)
    public void waitForMatchesLoaded() {
        page.waitForSelector(".match-card");
    }

    public void waitForStatusMessage() {
        page.waitForSelector("[data-testid='status-message']");
    }

    // Status and validation
    public String getStatusMessage() {
        return statusMessage.innerText();
    }

    public boolean isStatusMessageVisible() {
        return statusMessage.isVisible();
    }

    // Match count and availability
    public int getMatchCount() {
        return matchCards.count();
    }

    public boolean hasMatches() {
        return getMatchCount() > 0;
    }

    // League information
    public List<String> getLeagueNames() {
        return leagueHeaders.allInnerTexts();
    }

    public int getLeagueCount() {
        return leagueHeaders.count();
    }

    // Match card details
    public String getMatchCardText(int index) {
        return matchCards.nth(index).innerText();
    }

    public List<String> getAllMatchCards() {
        return matchCards.allInnerTexts();
    }

    // Extract match details by index
    public MatchDetails getMatchDetails(int index) {
        Locator card = matchCards.nth(index);
        String text = card.innerText();

        // Parse format: "Date\nHome Team Score - Score Away Team"
        MatchDetails details = new MatchDetails();
        details.rawText = text;
        details.backgroundColor = card.evaluate("el => window.getComputedStyle(el).backgroundColor").toString();

        return details;
    }

    // Result color verification (based on Angular logic)
    public String getMatchResultColor(int index) {
        Locator card = matchCards.nth(index);
        return card.evaluate("el => window.getComputedStyle(el).backgroundColor").toString();
    }

    public boolean isHomeWinColor(int index) {
        // Home win: #b8f7b8 (light green)
        String color = getMatchResultColor(index);
        return color.contains("184") || color.contains("rgb(184, 247, 184)");
    }

    public boolean isAwayWinColor(int index) {
        // Away win: #f7b8b8 (light red)
        String color = getMatchResultColor(index);
        return color.contains("247, 184, 184") || color.contains("rgb(247, 184, 184)");
    }

    public boolean isDrawColor(int index) {
        // Draw: #f7f3b8 (light yellow)
        String color = getMatchResultColor(index);
        return color.contains("247, 243, 184") || color.contains("rgb(247, 243, 184)");
    }

    // Navigation to match details
    public void clickMatch(int index) {
        matchCards.nth(index).locator("a").click();
    }

    public void clickMatchByTeams(String homeTeam, String awayTeam) {
        Locator matchCard = matchCards.filter(
            new Locator.FilterOptions()
                .setHasText(homeTeam)
                .setHasText(awayTeam)
        ).first();
        matchCard.locator("a").click();
    }

    // Utility: Inner class for match details
    public static class MatchDetails {
        public String rawText;
        public String backgroundColor;

        public MatchDetails() {
        }
    }
}
