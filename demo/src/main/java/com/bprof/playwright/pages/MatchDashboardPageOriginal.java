package com.bprof.playwright.pages;


import java.util.List;

import com.bprof.playwright.elements.MatchDashboardElements;

import com.microsoft.playwright.*; // import Page; Locator.
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MatchDashboardPageOriginal extends MatchDashboardElements {

    public MatchDashboardPageOriginal(Page page) {
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

    public void waitForStatusMessage(String expected) {
        assertThat(statusMessage).containsText(expected); // containsText instead of hasText due to Angular whitespace and change detection (or filter/FilterOptions())
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
        matchCards.first().scrollIntoViewIfNeeded(); 
        matchCards.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        matchCards.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        matchCards.first().click();
    }


    public void clickMatchById(String matchId) {
        Locator card = page.locator("a[href*='/match/" + matchId + "']");
        card.scrollIntoViewIfNeeded();
        card.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        card.click();
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