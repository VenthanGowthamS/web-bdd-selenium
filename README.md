# web-bdd-selenium

**Behaviour-driven browser automation for a login flow, using Cucumber, Selenium WebDriver and Gradle.**

This is one of my early test-automation projects (2021). The tests are written as readable scenarios and driven with data tables, so one scenario covers several sets of credentials.

## What it tests

```gherkin
@smoke
Scenario Outline: Login functionality validation
  Given I want to launch by "<URL>" in chrome browser
  When I enter username in discovery login page as "<Name>"
  Then I enter my valid password as "<Password>"
  And I click the login button

  Examples:
    | URL | Name | Password |
    | ... | ...  | ...      |
```

| Scenario | Tag | Status |
|---|---|---|
| Valid login (data-driven, several users) | `@smoke` | Implemented: opens Chrome, fills the form through a page object and submits |
| Invalid login shows a failure | `@regression` | Scenario written, step definitions still stubbed (unfinished) |

The test data in the feature file is sample data for practice, not real accounts.

## How it's built

| Piece | File | Role |
|---|---|---|
| Feature file | `src/test/resources/Discovery.feature` | Gherkin scenarios with `Scenario Outline` + `Examples` tables |
| Runner | `src/test/java/discoveryProject/discoveryrunner.java` | JUnit Cucumber runner: picks features, glue and tags, writes an HTML report to `target/cucumber` |
| Step definitions | `src/test/java/gluecodes/stepdefinition.java` | Glue code: starts ChromeDriver and maps each sentence to browser actions |
| Page object | `src/test/java/pages/loginpage.java` | Login screen elements (username, password, login button) |

**Stack:** Java · Selenium WebDriver 3.141 · Cucumber-JVM 1.2 · JUnit 4 · Gradle

## Run it

1. Install Chrome and a matching [ChromeDriver](https://googlechromelabs.github.io/chrome-for-testing/) on your `PATH`. The binary is no longer stored in this repo.
2. Run:

   ```bash
   ./gradlew test
   ```

3. Open the HTML report at `target/cucumber/index.html`.

To run a different set of scenarios, change `tags` in the runner (for example `@smoke`).

## What I'd do differently today

- Finish the negative-login steps and assert on the error message.
- Upgrade to current Cucumber (`io.cucumber`) and Selenium 4, which manages drivers itself.
- Load test credentials from environment variables instead of the feature file.

These early suites taught me to test behaviour, not just code. That's the same idea behind my AI agent evaluation work in [agent-evals-lab](https://github.com/VenthanGowthamS/agent-evals-lab).
