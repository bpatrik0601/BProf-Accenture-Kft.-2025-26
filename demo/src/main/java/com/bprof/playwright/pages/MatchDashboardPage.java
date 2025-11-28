package com.bprof.playwright.pages;

import java.util.List;

import com.bprof.playwright.elements.MatchDashboardElements;

import com.microsoft.playwright.*; // import Page; Locator.

public class MatchDashboardPage extends MatchDashboardElements {

    public MatchDashboardPage(Page page) {
        super(page);
    }

    // Elements
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
    
    // routerLink --> \href\ --> CSS (non-recursive) OR XPath (recursive) way of searching?:
    /*
    CSS selector, non-recursive: In Playwright (and also in Selenium), CSS-based searches are generally faster because the browser
     natively supports them. However, CSS selectors cannot search recursively through the DOM tree. They can only select elements
     based on their direct relationships (parent-child, sibling, etc.).

    XPath, recursive: CSS searches cannot perform as "deep" searches as XPath can, where you can search at any level using
     // or /*. However, XPath queries are generally slower because they require more processing to traverse the DOM tree.

    When to use which?:
    - CSS: if you want simple, stable, and easily maintainable selectors (e.g., .match-card, #username, button[type=submit]).
    - XPath: if you need to search within a more complex hierarchy (e.g., "find the a tag that is inside a div and contains 
       the text X").

    In larger projects, it's advisable to use CSS by default and only use XPath when recursive searching is truly necessary.

    Here, in this method's case --> "href*='/match/..." is a CSS attribute selector:
    - href*="..." → this means that the href attribute contains the specified text.
        This is very useful for routerLinks because you don't have to provide the full URL, just a part of it.

    In XPath it would be expressed as "//a[contains(@href,'/match/...')]".
        This recursively searches for all <a> tags whose href attribute contains the text.

    CSS vs XPath summarization here:
    - The CSS version (a[href*='/match/...']) is faster and shorter.
    - The XPath version (//a[contains(@href,'/match/...')]) is more flexible if you need to combine more complex conditions.
    */

    public void waitForMatches() {
    page.waitForSelector(".match-card"); // wait until at least one match card is present
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
