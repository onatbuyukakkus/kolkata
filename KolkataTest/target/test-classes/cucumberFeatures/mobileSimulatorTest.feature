Feature: Mobile scenarios

  Scenario: Creating a new customer with a bank account
    Given I have my CPR "696969-6969", my first name "Gabriel" and my last name "Angel"
    When  I create a user with an existing bank account
    Then  I get an user created response

  Scenario: Creating a new customer without a bank account
    Given I have my CPR "31313131", my first name "Israfil" and my last name "Angel"
    When  I create a user without an existing bank account
    Then  I get an user does not have a bank account response

  Scenario: Creating an existing customer
    Given I have my CPR "696969-6969", my first name "Gabriel" and my last name "Angel"
    When  I create a user with an existing bank account
    And   I get an user created response
    And 	  I create a user with an existing bank account
    Then  I get a user already exists response
    
  Scenario: Getting a barcode with a valid CPR
    Given I have my CPR "696969-6969", my first name "Gabriel" and my last name "Angel"
    When  I request a new barcode with a cpr "696969-6969"
    Then  I get my barcode
    
  Scenario: Getting a barcode with an invalid CPR
    Given I have my CPR "696969-6969", my first name "Gabriel" and my last name "Angel"
    When  I request a new barcode with a cpr "696969-1111"
    Then  I get a couldn't find user response

#  Examples:
#    | cpr |
#    | 161288-1010 |
#    | 570157-5156 |
#    | 587517-6807 |
#    | 760762-5715 |
#   	Examples:
#    	|cpr|
#    	|"161288-1010"		 |
#Scenario Outline: feeding a suckler cow
#  Given the cow weighs <weight> kg
#  When we calculate the feeding requirements
#  Then the energy should be <energy> MJ
#  And the protein should be <protein> kg

#  Examples:
#    | weight | energy | protein |
#    |    450 |  26500 |     215 |
#    |    500 |  29500 |     245 |
#    |    575 |  31500 |     255 |
#    |    600 |  37000 |     305 |