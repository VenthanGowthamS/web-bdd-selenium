package login.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Entry point for "./gradlew test". Runs every scenario under src/test/resources/features.
 * Pick a subset with: ./gradlew test -Dcucumber.filter.tags="@smoke"
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {"login.steps"},
        plugin = {"pretty", "html:build/reports/cucumber/report.html"}
)
public class RunCucumberTest {
}
