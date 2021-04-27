package login.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import login.pages.LoginPage;
import login.pages.SecureAreaPage;
import login.support.DriverFactory;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginSteps {

    private final WebDriver driver = DriverFactory.get();
    private final LoginPage loginPage = new LoginPage(driver);
    private final SecureAreaPage secureArea = new SecureAreaPage(driver);

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage.open();
    }

    @Given("I am logged in as {string} with password {string}")
    public void iAmLoggedInAs(String username, String password) {
        loginPage.logIn(username, password);
        assertTrue("Login should succeed before this scenario continues", secureArea.isDisplayed());
    }

    @When("I log in as {string} with password {string}")
    public void iLogInAs(String username, String password) {
        loginPage.logIn(username, password);
    }

    @When("I log out")
    public void iLogOut() {
        secureArea.logOut();
    }

    @Then("I should be in the secure area")
    public void iShouldBeInTheSecureArea() {
        assertTrue("Expected the secure area after logging in", secureArea.isDisplayed());
    }

    @Then("I should still be on the login page")
    public void iShouldStillBeOnTheLoginPage() {
        assertTrue("Expected to stay on the login page", loginPage.isDisplayed());
    }

    @Then("I should see the message {string}")
    public void iShouldSeeTheMessage(String expected) {
        assertEquals(expected, LoginPage.flashMessage(driver));
    }
}
