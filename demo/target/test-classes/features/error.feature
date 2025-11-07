(Gherkin / BDD) --> Give (What software will look like to user) -> When (Things that the user will do) -> Then (What the user should expect):

Feature: Error handling
  Verify system behavior when JSON is invalid

  Scenario: Invalid JSON data triggers an error message
    Given the dashboard is opened
    When the system loads "invalidData.json"
    Then an error message should appear