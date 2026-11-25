package pages.orangehrm;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** PIM > Add Employee. */
public class AddEmployeePage extends HrmBasePage {

    private final By firstName = By.name("firstName");
    private final By lastName = By.name("lastName");
    private final By saveButton = By.xpath("//button[@type='submit'][normalize-space()='Save']");
    private final By firstNameError = By.xpath(
            "//input[@name='firstName']/ancestor::div[contains(@class,'oxd-input-group')]"
                    + "//span[contains(@class,'oxd-input-field-error-message')]");

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    @Step("Wait for the Add Employee form")
    public AddEmployeePage waitUntilLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        waitForLoader();
        return this;
    }

    public boolean isOnAddEmployeeUrl() {
        return urlContains("/pim/addEmployee");
    }

    public boolean isFirstNameFieldDisplayed() {
        return isDisplayed(firstName);
    }

    public boolean isLastNameFieldDisplayed() {
        return isDisplayed(lastName);
    }

    @Step("Enter first name: {value}")
    public AddEmployeePage enterFirstName(String value) {
        type(firstName, value);
        return this;
    }

    @Step("Enter last name: {value}")
    public AddEmployeePage enterLastName(String value) {
        type(lastName, value);
        return this;
    }

    @Step("Click Save")
    public void clickSave() {
        log.info("Clicking Save");
        click(saveButton);
    }

    @Step("Save the employee")
    public PersonalDetailsPage saveAndOpenDetails() {
        clickSave();
        return new PersonalDetailsPage(driver);
    }

    @Step("Read the First Name validation error")
    public String getFirstNameError() {
        return textOf(firstNameError);
    }
}
