package pages.orangehrm;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

/** PIM > Employee List. */
public class EmployeeListPage extends HrmBasePage {

    private final By employeeNameInput = By.xpath("//label[normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By resultRows = By.cssSelector("div.oxd-table-body div.oxd-table-card");
    // Toast pop-up OR the inline message under the table. Deliberately NOT the autocomplete
    // drop-down, which also says "No Records Found" while you type and would give a false pass.
    private final By noRecords = By.xpath(
            "//div[contains(@class,'oxd-toast')]//p[normalize-space()='No Records Found']"
                    + " | //div[contains(@class,'orangehrm-horizontal-padding')]//span[normalize-space()='No Records Found']");

    public EmployeeListPage(WebDriver driver) {
        super(driver);
    }

    public void waitUntilLoaded() {
        wait.until(ExpectedConditions.urlContains("/pim/viewEmployeeList"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        waitForLoader();
    }

    @Step("Search for employee: {name}")
    public EmployeeListPage searchByName(String name) {
        log.info("Searching employee '{}'", name);
        type(employeeNameInput, name);
        click(searchButton);
        waitForLoader();
        return this;
    }

    /** Table rows as single normalised strings: "0312 Peter Anderson Software Engineer ...". */
    public List<String> getResultRows() {
        return driver.findElements(resultRows).stream()
                .map(r -> r.getText().replaceAll("\\s+", " ").trim())
                .collect(Collectors.toList());
    }

    @Step("Check the results table contains '{name}'")
    public boolean resultsContain(String name) {
        String needle = name.toLowerCase();
        try {
            return wait.until(d -> getResultRows().stream().anyMatch(r -> r.toLowerCase().contains(needle)));
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check the 'No Records Found' message is shown")
    public boolean isNoRecordsFoundDisplayed() {
        return isDisplayed(noRecords);
    }
}
