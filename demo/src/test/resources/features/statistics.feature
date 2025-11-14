(Gherkin / BDD) --> Give (What software will look like to user) -> When (Things that the user will do) -> Then (What the user should expect):

Feature: Statistics display
  Verify that the new page loads and displays the selected match's statistics

  Scenario: Load and display match statistics
    Given the dashboard is opened
    When the system loads "match-statistics.json"
    And the user selects match "1003"
    Then the statistics should be shown on screen
