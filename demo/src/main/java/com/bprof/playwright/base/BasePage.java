package com.bprof.playwright.base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;

public abstract class BasePage {
    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    // Common utility methods for all pages
    public String getTitle() {
        return page.title();
    }

    public String getUrl() {
        return page.url();
    }

    public void navigate(String url) {
        page.navigate(
            url,
            new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.NETWORKIDLE)
        );
    }
}
