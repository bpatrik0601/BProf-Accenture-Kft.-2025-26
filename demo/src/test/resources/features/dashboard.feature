(Gherkin / BDD) --> Give (What software will look like to user) -> When (Things that the user will do) -> Then (What the user should expect):

Feature: Dashboard display
  Verify that the dashboard shows correct match information

  Scenario: Load and display team data
    Given the dashboard is opened
    When the system loads "matches.json"
    Then the home and away teams should be shown