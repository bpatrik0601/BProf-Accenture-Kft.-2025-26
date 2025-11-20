# (Gherkin / BDD) --> Give (What software will look like to user) -> When (Things that the user will do) -> Then (What the user should expect):

Feature: Matches display
  Verify that the data exists and can be loaded in order to be displayed

  Scenario: Load and display match data between famous teams (from static JSON)
    Given the match data exists
    When the system loads matches' data "matches.json"
    Then the home and away teams should be shown