package login.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** The page a user reaches after a successful login. */
public class SecureAreaPage {

    private final WebDriver driver;

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutButton;

    public SecureAreaPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isDisplayed() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains("/secure"));
        return logoutButton.isDisplayed();
    }

    public void logOut() {
        logoutButton.click();
    }
}
