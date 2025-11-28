package com.bprof.playwright.elements;

import com.microsoft.playwright.Page;

import com.bprof.playwright.base.BasePage;
import com.bprof.playwright.wrappers.GeneralElementWrapper;

public class MatchDashboardElements extends BasePage {

    // Selector constants – Separation for better maintainability
    private static final String STATUS_MESSAGE_SELECTOR = "[data-testid='status-message']";
    private static final String LEAGUE_HEADERS_SELECTOR = ".match-list h3";
    private static final String MATCH_CARD_SELECTOR = ".match-card";

    // Elements
    protected final GeneralElementWrapper statusMessage;
    protected final GeneralElementWrapper leagueHeaders;
    protected final GeneralElementWrapper matchCards;

    public MatchDashboardElements(Page page) {
        super(page);
        
        this.statusMessage = new GeneralElementWrapper(page.locator(STATUS_MESSAGE_SELECTOR));
        this.leagueHeaders = new GeneralElementWrapper(page.locator(LEAGUE_HEADERS_SELECTOR));
        this.matchCards = new GeneralElementWrapper(page.locator(MATCH_CARD_SELECTOR));
    }
    
}
