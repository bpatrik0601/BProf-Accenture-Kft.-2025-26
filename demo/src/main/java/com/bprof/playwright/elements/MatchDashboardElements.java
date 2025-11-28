package com.bprof.playwright.elements;

import com.microsoft.playwright.Page;

import com.bprof.playwright.base.BasePage;
//import com.bprof.playwright.wrappers.GeneralElementWrapper;
import com.microsoft.playwright.Locator;

public class MatchDashboardElements extends BasePage {

    // Selector constants – Separation for better maintainability
    private static final String STATUS_MESSAGE_SELECTOR = "[data-testid='status-message']";
    private static final String LEAGUE_HEADERS_SELECTOR = ".match-list h3";
    private static final String MATCH_CARD_SELECTOR = ".match-card";

    // Elements as Locators
    protected final Locator statusMessage;
    protected final Locator leagueHeaders;
    protected final Locator matchCards;

    public MatchDashboardElements(Page page) {
        super(page);

        this.statusMessage = page.locator(STATUS_MESSAGE_SELECTOR);
        this.leagueHeaders = page.locator(LEAGUE_HEADERS_SELECTOR);
        this.matchCards = page.locator(MATCH_CARD_SELECTOR);
    }
    
}
