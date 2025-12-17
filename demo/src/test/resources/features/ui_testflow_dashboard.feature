# (Gherkin / BDD) --> Give (What software will look like to user) -> When (Things that the user will do) -> Then (What the user should expect):

Feature: Matches' Dashboard TestFlow
  As a football fan
  I want to see matches grouped by league
  So that I can quickly navigate to match details

  Scenario: Status message changes after matches load
    Given I open the match dashboard
    Then I should see the status message "Loading matches..."
    When the matches are loaded
    Then I should see the status message "Matches loaded"

  Scenario: League headers are displayed
    Given I open the match dashboard
    When the matches are loaded
    Then I should see the following league headers:
      | La Liga         |
      | Premier League  |
      | Champions League|
    And I should not see "Ligue 1"
    And I should not see "Serie A"
    And I should not see "Premier League"

  Scenario: Match count greater than zero
    Given I open the match dashboard
    When the matches are loaded
    Then the match count should be greater than 0

  Scenario: Match count matches JSON data
    Given I open the match dashboard
    When the matches are loaded
    Then the match count should be 8

  Scenario: Match count grouped by league
    Given I open the match dashboard
    When the matches are loaded
    Then La Liga should have 3 matches

  Scenario: Match card content
    Given I open the match dashboard
    When the matches are loaded
    Then the first match card should contain:
      | Borussia Dortmund |
      | RB Leipzig        |
      | 4 - 0             |

  Scenario: Click match navigates to details
    Given I open the match dashboard
    When I click the match with id "1001"
    Then I should be navigated to "/match/1001"
