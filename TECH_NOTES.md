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

## Design Decision

In CI environments, synchronization is performed using semantic and test-specific DOM markers (`data-testid`) combined with Playwright `Locator.waitFor()` calls.

This approach avoids flaky timing issues caused by asynchronous Angular rendering and ensures deterministic execution in headless CI/CD pipelines.

### Asynchronous Data Loading Handling

The Match Details page loads its statistics asynchronously via Angular HttpClient.
Originally, the Playwright tests waited only for the component container (`.match-details`) to appear, which proved insufficient in CI environments.

To ensure stability, a data-driven wait strategy was introduced.
The tests now wait until the expected number of statistic list items is rendered in the DOM before accessing their values.

This approach guarantees that assertions are executed only after the actual data has been fully loaded, eliminating flaky timeouts and false failures.

(Note: Although Playwright supports NETWORKIDLE navigation waits, this project primarily relies on DOM-based conditions for Angular pages, as asynchronous data loading may keep the network active even after the UI is ready.

In Angular applications, components frequently re-render DOM nodes after async data binding.
Therefore, Playwright locators should not be cached as fields for dynamic content.
Instead, locators must be queried fresh at interaction time to avoid stale references.)

### Context Summarization: Angular Async Rendering & Locator Freshness
Angular frequently re-renders DOM nodes after:
- HttpClient responses
- Change detection cycles
- Async pipes
- Component lifecycle hooks

#### Problem Observed
Caching Playwright locators as fields for dynamic content can lead to:
- Stale references
- Missing elements
- Timeouts

#### Design Decision
Locators for dynamic content must be:
- Queried fresh at interaction time
- Not stored as fields unless static
- Used through page.locator(...) inside methods
This ensures:
- Always up-to-date DOM references
- No stale element errors
- CI stability

### Context Summarization: Playwright Java – Avoiding waitForFunction Pitfalls
Playwright Java executes waitForFunction() by injecting the provided string into an internal JavaScript eval() context.

If the string is not a valid JS function, Playwright throws:
```bash
SyntaxError: Unexpected token '.'
```
This occurred when using:
```java
page.waitForFunction("document.querySelectorAll('.match-details ul li').length === 4");
```

#### Root Cause
Playwright expects a *function*, not a raw expression.

#### Correct Usage
```java
page.waitForFunction("() => document.querySelectorAll('.match-details ul li').length >= 4");
```

#### Preferred Alternative
For Angular apps, DOM-based waits are more stable:
```java
page.waitForSelector(".match-details ul li");
```
This avoids:
- JS evaluation errors
- Timing flakiness
- CI-specific rendering delays



# Context: Transient UI States and Test Determinism (Why Transient UI States Are Not Tested in E2E)

During the implementation of Playwright-based E2E tests, an explicit test case was initially created
to verify the temporary loading state of the dashboard ("Loading matches...").

Although this state exists in the application logic (Angular applications often display such short-lived loading states), it was intentionally removed from the final automated E2E test suite.

## Reasoning

The loading message represents a **transient UI state**:
- It may only appear for a very short time (milliseconds)
- In fast environments (local runs, CI, production builds with mock data) it may not appear at all thus won't ever be observable
- Playwright cannot reliably assert states that occur before the test runner gains control (these are highly timing-dependent)

As a result, asserting the loading message leads to **flaky tests**:
- Tests may pass or fail depending on timing (such as false negatives)
- CI environments behave differently than local development
- Increasing timeouts or adding explicit waits does not guarantee stability (CI instability)

## Design Decision

The E2E test suite focuses and validates exclusively on **stable, business-relevant states**:
- Final rendered content
- Deterministic DOM structure
- Post-loading UI states (e.g. "Matches loaded")

Transient states such as loading indicators are treated as:
- Implementation details
- Suitable for unit or component-level testing
- Unsuitable for deterministic end-to-end verification
(They are documented but not asserted.)

This decision improves:
- CI reliability
- Long-term maintainability
- Clarity of test intent

The existence and behavior of the loading state remains documented for demonstration purposes,
but it is not enforced by automated E2E assertions.

(Transient UI states (such as short-lived loading messages) are documented for demonstration purposes,
but the test suite primarily validates stable, end-user-visible states to ensure deterministic execution.)


These choices were made to be ensuring deterministic execution and improved stability in CI/CD environments.



# Context: Serving Angular 17+ Build Output in CI (Why Static Serving Is Required for Angular 17+ Builds)
Starting from Angular 17, the default application builder `@angular/build:application` produces a different build output structure compared to earlier Angular versions, which may cause building issues.

## Build Output Structure

Instead of placing `index.html` and static assets directly in the `dist/<app-name>` directory, Angular now (17+) generates the following, new layout/output structure:

dist/<app-name>/
├── browser/
├── index.html
├── assets/
├── main.<hash>.js

The actual browser application is located inside the `browser/` directory.

## Common CI Pitfall

When serving the application in CI using a static HTTP server (e.g. `http-server`), pointing the server to the build root directory will result in:
- `index.html` not being found
- Static assets returning HTTP 404
- Angular application not bootstrapping
- End-to-end tests (Playwright) timing out

### Correct Static Server Configuration and Approach

To ensure the application and assets are accessible, the static server must serve the `browser/` subdirectory:
```bash
npx http-server dist/<app-name>/browser -p 4200 --fallback index.html
```
This guarantees and ensures:
- Correct loading / resuliton of index.html
- Proper resolution of /assets/** paths
- Reliable and stable Angular application startup / bootstrap in CI environments

### Context Summarization: Angular Route Handling in CI (SPA + Static Server + Fallback)
When serving an Angular 17+ production build in CI using a static HTTP server (e.g. http-server), the application relies on a Single Page Application (SPA) fallback mechanism:
- Any unknown route → index.html
- Angular bootstraps and interprets the URL
- Component initialization depends on route parameters being preserved

#### Problem Observed
Direct navigation to deep routes such as:
```
http://localhost:4200/match/1001
```
works locally (ng serve) but *fails in CI* when served from:
```
dist/<app-name>/browser
```
Symptoms:
- Angular router receives *no route parameters*
- 'ActivatedRoute.snapshot.paramMap.get('id')' returns *null*
- Component logic does not execute
- Playwright tests time out waiting for DOM elements that never appear

#### Root Cause
Static servers with SPA fallback serve index.html for all routes, but *route parameters are not guaranteed to be preserved* during initial load in all environments.

Local dev server (ng serve) behaves differently and masks the issue.

#### Design Decision
E2E tests must *never* directly navigate to deep Angular routes in CI.

Instead, tests must:
1. Open the dashboard page
2. Click the routerLink that leads to the details page
3. Allow Angular to handle navigation internally

This ensures:
- Router parameters are preserved
- Components initialize correctly
- Asynchronous data loading occurs as expected
- Playwright sees the correct DOM state

This pattern is now enforced across all E2E tests.



# Cucumber Scenario Isolation & Background Usage
Cucumber executes each scenario in complete isolation:
- New class instance
- New state
- New Playwright page reference
- No shared variables between scenarios

## Problem Observed
Scenarios that did not explicitly include:
```Gherkin
Given I open the match details for "1001"
When the page is loaded
```
attempted to access details, which was null.

This resulted in:
```
Cannot invoke "...waitForStatisticsLoaded()" because "details" is null
```

## Root Cause
A Background block was introduced:
```Gherkin
Background:
  Given I open the match details for "1001"
  And the page is loaded
```
This ensures:
- Deterministic initialization for every scenario
- No null state
- Clean separation between setup and assertions
- Readable, maintainable BDD structure



# Context Separation of Concerns in CI (UI Tests vs Static Analysis)
The CI pipeline contains two independent jobs:
1. ui-tests
- Builds Angular
- Serves the production build
- Runs Playwright E2E tests
- Produces JUnit + Cucumber reports

2. static-analysis
- Runs Checkstyle, SpotBugs, import optimization
- Runs Angular ESLint
- Does *not* run UI tests

## Problem Observed
The static-analysis job originally executed:
```bash
mvn verify
```

This unintentionally triggered:
- Playwright tests
- Without a running Angular server

Resulting in:
```
net::ERR_CONNECTION_REFUSED at http://localhost:4200/
```

## Design Decision
Static analysis must *never* run UI tests.
The job now uses:
```bash
mvn -DskipTests=true verify
```
This ensures:
- Code quality checks run
- UI tests do not run
- CI pipeline remains fast, deterministic, and logically separated