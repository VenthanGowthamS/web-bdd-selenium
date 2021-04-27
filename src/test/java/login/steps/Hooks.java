package login.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import login.support.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before
    public void openBrowser() {
        DriverFactory.start();
    }

    @After
    public void closeBrowser(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] png = ((TakesScreenshot) DriverFactory.get()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(png, "image/png", "screen at failure");
            }
        } finally {
            DriverFactory.quit();
        }
    }
}
