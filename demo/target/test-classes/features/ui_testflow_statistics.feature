# (Gherkin / BDD) --> Give (What software will look like to user) -> When (Things that the user will do) -> Then (What the user should expect):

Feature: Match Statistics' Details TestFlow
  As a football fan
  I want to see match details
  So that I can check statistics

  Scenario: Statistics visible after load
    Given I open the match details for "1001"
    When the page is loaded
    Then the team names should be visible

  Scenario: Team names and date displayed
    Given I open the match details for "1001"
    When the page is loaded
    Then the team names should be visible
    And the match date should be visible

  Scenario: Score displayed correctly
    Given I open the match details for "1001"
    When the page is loaded
    Then the score should be "Score: 3 - 1"

  Scenario: Goals statistic
    Given I open the match details for "1001"
    When the page is loaded
    Then the goals statistic should be "3 - 1"

  Scenario: Shots on target statistic
    Given I open the match details for "1001"
    Then the shots on target statistic should be "17 - 6"

  Scenario: Possession statistic
    Given I open the match details for "1001"
    Then the possession statistic should be "68% - 32%"

  Scenario: Fouls statistic
    Given I open the match details for "1001"
    Then the fouls statistic should be "8 - 14"

  Scenario: All statistics listed
    Given I open the match details for "1001"
    Then the statistics should include:
      | Goals: 3 - 1          |
      | Shots on Target: 17 - 6 |
      | Possession: 68% - 32% |
      | Fouls: 8 - 14         |
