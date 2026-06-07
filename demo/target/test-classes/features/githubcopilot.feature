# Gherkin written by GitHub Copilot Agentic AI

Feature: View grouped football matches by league on dashboard
  As a football fan
  I want to see football matches grouped by league on the dashboard
  So that I can quickly review match results and navigate to the selected match details

  Scenario: Matches are grouped and displayed by league on dashboard
    Given the dashboard contains matches from multiple leagues
      And matches are from "Premier League", "La Liga", and "Serie A"
    When I load the dashboard
    Then I should see a separate section for each league
      And "Premier League" section displays all Premier League matches
      And "La Liga" section displays all La Liga matches
      And "Serie A" section displays all Serie A matches
      And each match shows the teams, kick-off time, and current score

  Scenario: Navigate to match details from grouped league view
    Given the dashboard displays matches grouped by "Premier League" and "La Liga"
      And the "Premier League" section contains a match between "Manchester United" and "Liverpool"
    When I click on the "Manchester United vs Liverpool" match
    Then I should be navigated to the detailed match page
      And the match page displays the match title "Manchester United vs Liverpool"
      And the match page shows the full match information including date, venue, and player statistics

  Scenario: Collapsed and expanded league sections
    Given the dashboard displays matches grouped by league
    When I collapse the "La Liga" section
    Then the "La Liga" matches should be hidden
      And the section should display a collapsed indicator
    When I expand the "La Liga" section
    Then all "La Liga" matches should be visible again