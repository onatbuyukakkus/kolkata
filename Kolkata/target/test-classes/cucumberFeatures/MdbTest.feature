@tag
Feature: Barcode creation

  @tag1
  Scenario: Create user from received MDB
    Given A message is received 
    When The message is processed
    Then The message type exists 