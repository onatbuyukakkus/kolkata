Feature: Merchant scenarios

  Scenario: Adding a non-existing merchant with a bank account
    Given I have my CVR number "45612378" and my company name "KolkataAngels_Store"
    When I create my account with an existing bank account
    Then I get a merchant created response

  Scenario: Adding a non-existing merchant without bank account
    Given I have my CVR number "5123762136" and my company name "McDonalds_Lyngby"
    When I create my account without an existing bank account
    Then I get a merchant does not have a bank account response

  Scenario: Adding an existing merchant
    Given I have my CVR number "45612378" and my company name "KolkataAngels_Store"
    When I create my account with an existing bank account
    And I create my account with an existing bank account
    Then I get a merchant already exists response

  Scenario: Doing a transaction with an invalid barcode
    Given I have my CVR number "45612378" and my company name "KolkataAngels_Store"
    And I create my account with an existing bank account
    And I read an invalid barcode "invalidBarcode-1"
    When I make a transaction with my CVR number "45612378"
    Then I get an invalid barcode response

  Scenario: Doing a transaction with a valid barcode
    Given I have my CVR number "45612378" and my company name "KolkataAngels_Store"
    And I create my account with an existing bank account
    And I read a valid barcode
    When I make a transaction with my CVR number "45612378"
    Then I get a transaction successful response

  Scenario: Doing a transaction with insufficient balance
    Given I have my CVR number "45612378" and my company name "KolkataAngels_Store"
    And I create my account with an existing bank account
    And I read a valid barcode
    When I make a transaction with my CVR number "45612378" and high balance "99999"
    Then I get a balance will be negative response
