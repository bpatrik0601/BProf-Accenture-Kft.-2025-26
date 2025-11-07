Gherkin / BDD --> API testing:

Feature: API Testing with Playwright
  As a QA engineer
  I want to validate API endpoints
  So that I can ensure correct responses

  Scenario: Validate GET request returns correct status and body
    Given the API client is initialized
    When I send a GET request to "https://jsonplaceholder.typicode.com/posts/1"
    Then the response status should be 200
    And the response body should contain "userId"

  Scenario: Validate POST request creates a resource
    Given the API client is initialized
    When I send a POST request to "https://jsonplaceholder.typicode.com/posts" with body:
      """
      {
        "title": "foo",
        "body": "bar",
        "userId": 1
      }
      """
    Then the response status should be 201
    And the response body should contain "id"