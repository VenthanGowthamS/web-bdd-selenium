package login.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** The public practice login page at the-internet.herokuapp.com/login (or -DbaseUrl). */
public class LoginPage {

    private static final String DEFAULT_BASE_URL = "https://the-internet.herokuapp.com";
    private static final By FLASH_MESSAGE = By.id("flash");

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "username")
    private WebElement username;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public LoginPage open() {
        driver.get(System.getProperty("baseUrl", DEFAULT_BASE_URL) + "/login");
        wait.until(ExpectedConditions.visibilityOf(username));
        return this;
    }

    public void logIn(String user, String pass) {
        username.clear();
        username.sendKeys(user);
        password.clear();
        password.sendKeys(pass);
        loginButton.click();
    }

    public boolean isDisplayed() {
        return driver.getCurrentUrl().endsWith("/login") && username.isDisplayed();
    }

    /** The banner shown after a login attempt, without its close button ("×"). */
    public static String flashMessage(WebDriver driver) {
        WebElement flash = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(FLASH_MESSAGE));
        return flash.getText().replace("×", "").trim();
    }
}
