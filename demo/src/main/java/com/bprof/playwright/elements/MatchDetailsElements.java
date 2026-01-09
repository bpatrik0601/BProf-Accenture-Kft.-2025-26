package com.bprof.playwright.elements;

import com.microsoft.playwright.Page;

import com.bprof.playwright.base.BasePage;
//import com.bprof.playwright.wrappers.GeneralElementWrapper;
import com.microsoft.playwright.Locator;

public class MatchDetailsElements extends BasePage {

    // Selector constants – Separation for better maintainability
    private static final String LOADING_SELECTOR = "p:has-text('Loading match statistics')";
    private static final String TEAM_NAMES_SELECTOR = "h3";
    private static final String SCORE_SELECTOR = "p:has-text('Score')";
    //private static final String STATISTICS_SELECTOR = "ul li";

    // Elements
    protected final Locator loadingMessage;
    protected final Locator teamNames;
    protected final Locator score;
    //protected final Locator statistics;

    public MatchDetailsElements(Page page) {
        super(page);
        
        this.loadingMessage = page.locator(LOADING_SELECTOR);
        this.teamNames = page.locator(TEAM_NAMES_SELECTOR);
        this.score = page.locator(SCORE_SELECTOR);
        //this.statistics = page.locator(STATISTICS_SELECTOR);
    }
}
