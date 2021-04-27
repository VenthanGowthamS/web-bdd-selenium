# web-bdd-selenium

**Behaviour-driven browser tests for a login flow: plain-English scenarios, real Chrome, one command.**

The scenarios describe what a user expects. Each step drives a real browser with Selenium and checks what actually appears on the page. The suite runs against [the-internet.herokuapp.com/login](https://the-internet.herokuapp.com/login), a public practice site built for test automation, so anyone can clone it and run it.

## What it tests

```gherkin
Feature: Login
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
```

| Scenario | Tag | What it proves |
|---|---|---|
| Valid login | `@smoke` | The right user reaches the secure area and is told so |
| Invalid login × 3 | `@regression` | Wrong username, wrong password and empty form are all rejected, and the user stays on the login page |
| Logout | `@regression` | Logging out really ends the session and says so |

The credentials are the practice site's public demo account, not real ones.

## How it's built

```
src/test/
├── resources/features/login.feature   ← scenarios in plain English
└── java/login/
    ├── runner/RunCucumberTest.java    ← JUnit entry point, HTML report
    ├── steps/LoginSteps.java          ← each sentence → browser actions + assertions
    ├── steps/Hooks.java               ← open/close Chrome; screenshot on failure
    ├── pages/LoginPage.java           ← page object: form, flash message
    ├── pages/SecureAreaPage.java      ← page object: secure area, logout
    └── support/DriverFactory.java     ← Chrome setup, headless option
```

- **Page Object Model:** locators live in one place per page, so steps read like the scenario.
- **Explicit waits:** the tests wait for the page state they need, with no fixed sleeps.
- **No driver binary in the repo:** WebDriverManager fetches the ChromeDriver that matches your Chrome.
- **Evidence on failure:** a screenshot is attached to the report when a scenario fails.

**Stack:** Java 8+ · Selenium WebDriver 3.141 · Cucumber-JVM 6.10 · JUnit 4 · WebDriverManager · Gradle

## Run it

You need Java and Google Chrome.

```bash
./gradlew test                                   # all scenarios, visible browser
./gradlew test -Dheadless=true                   # no window (CI)
./gradlew test -Dcucumber.filter.tags="@smoke"   # just the smoke test
```

The report is written to `build/reports/cucumber/report.html`.

Optional settings: `-DbaseUrl=` to point at another environment, and `-Dchromedriver=/path/to/chromedriver` to use a driver you already have.
