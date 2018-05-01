@tag
Feature: Barcode creation

  @tag1
  Scenario: Create a barcode for a user
    Given I am a user
    When I create a barcode
    Then I get a barcode