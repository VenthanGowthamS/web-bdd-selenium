package login.support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Starts and stops Chrome. Settings (all optional, passed with -Dname=value):
 *   headless=true      run without a visible window, e.g. on CI
 *   chromedriver=path  use this ChromeDriver instead of downloading a matching one
 */
public final class DriverFactory {

    private static WebDriver driver;

    private DriverFactory() {
    }

    public static WebDriver start() {
        String driverPath = System.getProperty("chromedriver");
        if (driverPath != null) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        } else {
            WebDriverManager.chromedriver().setup();
        }

        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1280,900");
        }
        driver = new ChromeDriver(options);
        return driver;
    }

    public static WebDriver get() {
        if (driver == null) {
            throw new IllegalStateException("Browser has not been started");
        }
        return driver;
    }

    /** Closes the browser if one was started. Safe to call when start() failed. */
    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
