# Scope of This Document
> **Note:**
> This file serves as a central place for collecting various technical notes that do not belong directly in the source code.  
> It may include explanations about selectors, Page Object Model design, CI/CD decisions, architectural reasoning, or any other topic that benefits from additional context.

### Summary
New sections of additional technical explanations, design decisions, and background notes can be added here as the project grows and evolves.

# Context: CSS <--> XPath

The following explanation originally appeared as an inline comment inside the `MatchDashboardPage` class.:
```java
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
```

It was part of a longer reasoning about how to select Angular routerLink elements using CSS attribute selectors versus XPath expressions.

While the explanation was technically correct and useful, it made the source file unnecessarily long and cluttered.  
To keep the Page Object Model classes clean, readable, and focused on behavior rather than theory, the detailed selector explanation was moved here into the technical notes.

This section preserves and summarizes the reasoning behind the decision, provides additional context, and serves as a reference for future contributors who want to understand why CSS selectors were chosen over XPath in this project.

## CSS vs XPath Selectors in Playwright

When working with Playwright (and also Selenium), it is important to understand the difference between CSS selectors and XPath, especially when dealing with Angular routerLinks or deeply nested elements.

This project intentionally prefers CSS selectors over XPath for most element interactions in order to maximize performance, readability, and long-term maintainability.

### CSS Selectors (non-recursive)
- Fastest option (browser-native)
- Clean, short, easy to maintain
- Cannot search recursively through the DOM
- Best for most cases: classes, IDs, attributes, simple hierarchies

Example:
```css
.match-card
a[href*='/match/']
button[type='submit']
```

### XPath Selectors (recursive)
- Can search at any depth in the DOM  
- More flexible for complex structures  
- Slightly slower due to DOM traversal  
- Useful when CSS cannot express the required relationship  

Example:
```xpath
//a[contains(@href, '/match/')]
```

### When to Use Which
- Use CSS by default for speed, readability, and maintainability
- Use XPath only when recursive traversal is required
- For Angular routerLinks, CSS attribute selectors (href*='...') are ideal

### Summary

CSS selectors are preferred in this project because they are faster, cleaner, and more stable.
XPath remains available for edge cases but is not needed for typical routerLink interactions.



# Context: CI-Optimization

In CI environment, Angular's asynchronous data loading is slower, so the Playwright tests initially did not wait deterministically for the DOM to render.

It has been fixed with navigation and a more stable wait strategy (`networkidle`):
>Instead of waiting for the final UI elements, the tests first synchronize on semantic and test-specific DOM markers (`data-testid`).
>Synchronization is implemented using Playwright Locator.waitFor() calls instead of raw selector waits.

## Further addition
In the CI pipeline, Playwright tests initially timed out.
The reason for the error was that the Angular application was built with the new @angular/build:application builder, without an explicit outputPath.
As a result, the static server served the wrong build directory and did not handle client-side routing.
The problem was solved by explicitly specifying the outputPath and using the SPA fallback mode of the http-server.

The tests were initially based on implicit timing.
After refactoring the Page Object Model, deterministic waiting was introduced, so explicit synchronization of transient UI states became necessary.

Playwright's Java assertion API is used because it provides implicit waiting on the appearance and state of the element, thus avoiding timing errors.

(Transient UI states (such as short-lived loading messages) are documented for demonstration purposes,
but the test suite primarily validates stable, end-user-visible states to ensure deterministic execution.)


These changes were made to be ensuring deterministic execution and improved stability in CI/CD environments.

### Design Decision

In CI environments, synchronization is performed using semantic and test-specific DOM markers (`data-testid`) combined with Playwright `Locator.waitFor()` calls.

This approach avoids flaky timing issues caused by asynchronous Angular rendering and ensures deterministic execution in headless CI/CD pipelines.
