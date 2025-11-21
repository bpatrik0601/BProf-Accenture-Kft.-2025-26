/* 
First, initial / prototype version page object model for a football match page using Playwright in Java. (--> originally MatchPage.java)
Will be used in PrototypeMatchTest.java for UI testing and later removed from the project.
*/

package com.bprof.playwright.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;

public class PrototypeMatchPage {
    private final Page page;

    // UI objects' selectors
    private final String hometeamSelector = "hometeam";
    private final String awayteamSelector = "awayteam";

    private final String scoreSelector = "score";
    private final String totalshotsSelector = "totalshots";
    private final String possessionSelector = "possession";
    
    public PrototypeMatchPage(Page page) {
        this.page = page;
    }

    public void goTo() {
        page.navigate("https://www.sofascore.com/football/match/levante-ud-real-madrid/EgbsZgb#id:14083426",
        new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        // for title testing: don't wait for the whole load event, first DOM only
    }

    public String getTitle() {
        return page.title();
    }

    public String getHomeTeam() {
        return page.textContent(hometeamSelector);
    }

    public String getAwayTeam() {
        return page.textContent(awayteamSelector);
    }

    public String getScore() { 
        return page.textContent(scoreSelector);
    } 
    
    public String getTotalShots() {
        return page.textContent(totalshotsSelector); 
    }

    public String getPossession() {
        return page.textContent(possessionSelector); 
    }
}