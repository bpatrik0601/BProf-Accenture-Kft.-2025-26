package com.bprof.playwright.pages;

import java.util.List;

import com.bprof.playwright.elements.MatchDashboardElements;

import com.microsoft.playwright.*; // import Page; Locator.

public class MatchDashboardPage extends MatchDashboardElements {

    public MatchDashboardPage(Page page) {
        super(page);
    }

    // Basic element getters
    public Locator getStatusMessage() {
        return statusMessage;
    }

    public List<String> getLeagueHeadersText() {
        return leagueHeaders.allInnerTexts();
    }

    public List<Locator> getAllMatchCards() {
        return matchCards.all();
    }

    public Locator getMatchCardByTeam(String teamName) {
        return matchCards.filter(new Locator.FilterOptions().setHasText(teamName));
    }

    
    // Actions --> utility methods

    public void waitForDashboardReady() {
        statusMessage.waitFor(); // wait until status message is present
        leagueHeaders.first().waitFor(); // wait until at least one league header is present
    }

    public void open() {
        page.navigate("http://localhost:4200/");
        waitForDashboardReady();
    }

    public void waitForMatches() {
        matchCards.first().waitFor(
            new Locator.WaitForOptions().setTimeout(60000)
        ); // wait until at least one match card is present
    }

    public String getFirstMatchCardText() {
        return matchCards.first().innerText();
    }

    public void clickFirstMatchCard() {
        matchCards.first().click();
    }


    public void clickMatchById(String matchId) {
        page.locator("a[href*='/match/" + matchId + "']").click();
    }

    public void clickMatchByTeam(String teamName) {
        getMatchCardByTeam(teamName).click();
    }

    public int getMatchCount() {
        return matchCards.count();
    }

    public String getStatusMessageText() {
        return getStatusMessage().innerText();
    }
}
