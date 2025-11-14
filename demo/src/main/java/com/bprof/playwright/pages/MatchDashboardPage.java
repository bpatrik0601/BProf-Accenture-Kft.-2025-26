package com.bprof.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MatchDashboardPage {
    private final Page page;

    // Selector constants –-> Separation for better maintainability
    private static final String STATUS_MESSAGE_SELECTOR = "p";
    private static final String LEAGUE_HEADERS_SELECTOR = ".match-list h3";
    private static final String MATCH_CARD_SELECTOR = ".match-card";

    public MatchDashboardPage(Page page) {
        this.page = page;
    }

    // Locators
    public Locator getStatusMessage() {
        return page.locator(STATUS_MESSAGE_SELECTOR);
    }

    public Locator getLeagueHeaders() {
        return page.locator(LEAGUE_HEADERS_SELECTOR);
    }

    public Locator getAllMatchCards() {
        return page.locator(MATCH_CARD_SELECTOR);
    }

    public Locator getMatchCardByTeam(String teamName) {
        return page.locator(MATCH_CARD_SELECTOR).filter(new Locator.FilterOptions().setHasText(teamName));
    }

    // Actions --> utility methods
    
    // routerLink --> href*
    public void clickMatchById(String matchId) {
        page.locator("a[href*='/match/" + matchId + "']").click();
    }

    public void clickMatchByTeam(String teamName) {
        getMatchCardByTeam(teamName).click();
    }

    public int getMatchCount() {
        return getAllMatchCards().count();
    }

    public String getStatusMessageText() {
        return getStatusMessage().innerText();
    }
}
