# (Gherkin / BDD) --> Give (What software will look like to user) -> When (Things that the user will do) -> Then (What the user should expect):

Feature: Error handling
  Verify system behavior when JSON is invalid

  Scenario: Invalid JSON data triggers an error message
    Given the invalid match data exists
    When the system loads the invalid file "invalid-matches.json"
    Then an error message should appear