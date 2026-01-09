Feature: Match Statistics' Details TestFlow

  Background:
    Given I open the match details for "1001"
    And the page is loaded

  Scenario: Statistics visible after load
    Then the team names should be visible

  Scenario: Team names and date displayed
    Then the team names should be visible
    And the match date should be visible

  Scenario: Score displayed correctly
    Then the score should be "Score: 3 - 1"

  Scenario: Goals statistic
    Then the goals statistic should be "3 - 1"

  Scenario: Shots on target statistic
    Then the shots on target statistic should be "17 - 6"

  Scenario: Possession statistic
    Then the possession statistic should be "68% - 32%"

  Scenario: Fouls statistic
    Then the fouls statistic should be "8 - 14"

  Scenario: All statistics listed
    Then the statistics should include:
      | Goals: 3 - 1            |
      | Shots on Target: 17 - 6 |
      | Possession: 68% - 32%   |
      | Fouls: 8 - 14           |
