package com.bprof.playwright.elements;

import com.microsoft.playwright.Page;

import com.bprof.playwright.base.BasePage;
import com.bprof.playwright.wrappers.GeneralElementWrapper;

public class MatchDetailsElements extends BasePage {

    // Selector constants – Separation for better maintainability
    protected static final String LOADING_SELECTOR = "p:has-text('Loading match statistics')";
    protected static final String TEAM_NAMES_SELECTOR = "h3";
    protected static final String SCORE_SELECTOR = "p:has-text('Score')";
    protected static final String STATISTICS_SELECTOR = "ul li";

    // Elements
    protected final GeneralElementWrapper loadingMessage;
    protected final GeneralElementWrapper teamNames;
    protected final GeneralElementWrapper score;
    protected final GeneralElementWrapper statistics;

    public MatchDetailsElements(Page page) {
        super(page);
        
        this.loadingMessage = new GeneralElementWrapper(page.locator(LOADING_SELECTOR));
        this.teamNames = new GeneralElementWrapper(page.locator(TEAM_NAMES_SELECTOR));
        this.score = new GeneralElementWrapper(page.locator(SCORE_SELECTOR));
        this.statistics = new GeneralElementWrapper(page.locator(STATISTICS_SELECTOR));
    }
    
}
