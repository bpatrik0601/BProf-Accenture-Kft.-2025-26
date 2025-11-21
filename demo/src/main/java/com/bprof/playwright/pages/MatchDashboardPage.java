package com.bprof.playwright.pages;

import com.bprof.playwright.elements.Element;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MatchDashboardPage {
    private final Page page;

    // Selector constants – Separation for better maintainability
    private static final String STATUS_MESSAGE_SELECTOR = "p";
    private static final String LEAGUE_HEADERS_SELECTOR = ".match-list h3";
    private static final String MATCH_CARD_SELECTOR = ".match-card";

    public MatchDashboardPage(Page page) {
        this.page = page;
    }

    // Elements
    public Element getStatusMessage() {
        return new Element(page.locator(STATUS_MESSAGE_SELECTOR));
    }

    public Element getLeagueHeaders() {
        return new Element(page.locator(LEAGUE_HEADERS_SELECTOR));
    }

    public Element getAllMatchCards() {
        return new Element(page.locator(MATCH_CARD_SELECTOR));
    }

    public Element getMatchCardByTeam(String teamName) {
        Locator locator = page.locator(MATCH_CARD_SELECTOR).filter(new Locator.FilterOptions().setHasText(teamName));
        return new Element(locator);
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
    public void clickMatchById(String matchId) {
        page.locator("a[href*='/match/" + matchId + "']").click();
    }

    public void clickMatchByTeam(String teamName) {
        getMatchCardByTeam(teamName).click();
    }

    public int getMatchCount() {
        return page.locator(MATCH_CARD_SELECTOR).count();
    }

    public String getStatusMessageText() {
        return getStatusMessage().getText();
    }
}
