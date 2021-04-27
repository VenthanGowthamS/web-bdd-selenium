Feature: Login
  As a registered user
  I want to log in to my secure area
  So that only I can see my account, and wrong details are clearly rejected

  Background:
    Given I am on the login page

  @smoke
  Scenario: Valid credentials open the secure area
    When I log in as "tomsmith" with password "SuperSecretPassword!"
    Then I should be in the secure area
    And I should see the message "You logged into a secure area!"

  @regression
  Scenario Outline: Invalid credentials are rejected
    When I log in as "<username>" with password "<password>"
    Then I should still be on the login page
    And I should see the message "<message>"

    Examples:
      | username  | password             | message                   |
      | wronguser | SuperSecretPassword! | Your username is invalid! |
      | tomsmith  | wrongpassword        | Your password is invalid! |
      |           |                      | Your username is invalid! |

  @regression
  Scenario: Logging out returns me to the login page
    Given I am logged in as "tomsmith" with password "SuperSecretPassword!"
    When I log out
    Then I should still be on the login page
    And I should see the message "You logged out of the secure area!"
