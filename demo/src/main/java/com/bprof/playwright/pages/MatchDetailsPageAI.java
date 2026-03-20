package com.bprof.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.List;

public class MatchDetailsPageAI {
    private final Page page;
    
    // Lokátorok
    private final String matchCards = ".match-card";
    private final String teamNameLabels = ".team-name";

    public MatchDetailsPageAI(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate("https://example.com/matches");
    }

    public int getMatchCount() {
        return page.locator(matchCards).count();
    }

    public List<String> getTeamNames() {
        return page.locator(teamNameLabels).allInnerTexts();
    }
}