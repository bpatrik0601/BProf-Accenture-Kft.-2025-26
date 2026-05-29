# Initial AI testing, left in for display

Feature: Displaying a list of matches

  Scenario: Successfully loading and displaying matches on the interface
    Given the user opens the matches subpage
    When the system loads the "matches.json" data file
    Then the names of the teams should appear in the list
    And the number of matches should match the data in the source file