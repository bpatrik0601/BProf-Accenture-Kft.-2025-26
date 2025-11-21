package com.bprof.playwright.elements;

import com.microsoft.playwright.Locator;

// Wrapper class for Playwright Locator to represent web elements
public class Element {
    private final Locator locator;

    public Element(Locator locator) {
        this.locator = locator;
    }

    public void click() {
        locator.click();
    }

    public String getText() {
        return locator.innerText();
    }

    public boolean isVisible() {
        return locator.isVisible();
    }

    public void type(String text) {
        locator.fill(text);
    }

    public Locator getLocator() {
        return locator;
    }
}
