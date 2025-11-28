# Gherkin / BDD --> API testing:

Feature: API Testing with Playwright
  As a QA engineer
  I want to validate API endpoints
  So that I can ensure correct responses

  Scenario: Validate matches.json returns correct status and body
    Given the API client is initialized
    When I send a GET request to "http://localhost:4200/assets/mock/matches.json"
    Then the response status should be 200
    And the response body should contain "events"

  Scenario: Validate match-statistics.json returns correct status and body
    Given the API client is initialized
    When I send a GET request to "http://localhost:4200/assets/mock/match-statistics.json"
    Then the response status should be 200
    And the response body should contain "1092"
