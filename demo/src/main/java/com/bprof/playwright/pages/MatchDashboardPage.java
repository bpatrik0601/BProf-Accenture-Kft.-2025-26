package com.bprof.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MatchDashboardPage {
    private final Page page;

    public MatchDashboardPage(Page page) {
        this.page = page;
    }

    public Locator getStatusMessage() {
        return page.locator("p");
    }

    public Locator getLeagueHeaders() {
        return page.locator(".match-list h3");
    }

    public Locator getAllMatchCards() {
        return page.locator(".match-card");
    }

    public Locator getMatchCardByTeam(String teamName) {
        return page.locator(".match-card >> text=" + teamName);
    }

    public void clickMatchById(String matchId) {
        page.locator("a[href='/match/" + matchId + "']").click();
    }

    public void clickMatchByTeam(String teamName) {
        getMatchCardByTeam(teamName).click();
    }
}
