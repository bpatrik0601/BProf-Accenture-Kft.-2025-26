package com.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MatchDetailsPage {
    private final Page page;

    public MatchDetailsPage(Page page) {
        this.page = page;
    }

    public Locator getLoadingMessage() {
        return page.locator("p:has-text('Loading match statistics')");
    }

    public Locator getTeamNames() {
        return page.locator("h3"); // .click((ClickOptions) options: null)); -> ennek vizsgalata a kovetkezo commit-okban
    }

    public Locator getScore() {
        return page.locator("p:has-text('Score')");
    }

    public Locator getStatisticByLabel(String label) {
        return page.locator("ul li:has-text('" + label + "')");
    }

    public String getGoals() {
        return getStatisticByLabel("Goals").innerText();
    }

    public String getShotsOnTarget() {
        return getStatisticByLabel("Shots on Target").innerText();
    }

    public String getPossession() {
        return getStatisticByLabel("Possession").innerText();
    }

    public String getFouls() {
        return getStatisticByLabel("Fouls").innerText();
    }
}
