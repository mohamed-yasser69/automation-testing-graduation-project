package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class EmployeesPage extends BasePage {

    // Fixed locators (they never change)
    private final By selectedCount = By.id("selectedCount");
    private final By editMessage = By.id("editMessage");

    public EmployeesPage(WebDriver driver) {
        super(driver);
    }

    // ---------- Dynamic locators: the employee name goes into the XPath ----------

    // The checkbox is in the cell BEFORE the name cell
    private By checkboxOf(String name) {
        // TODO 1: return a By.xpath that finds the checkbox in the row of this employee.
        //         Start from the cell with the name, then go to the cell before it.
        return null;
    }

    // Any cell of the row: the column name goes into the XPath too (no index needed)
    private By cellOf(String name, String column) {
        // TODO 2: return a By.xpath for the cell of this employee in this column.
        //         Every cell has a data-label attribute with its column name.
        return null;
    }

    // The Edit button is inside the last cell of the row
    private By editButtonOf(String name) {
        // TODO 3: return a By.xpath for the Edit button in the row of this employee.
        return null;
    }

    // The name cell itself (used to check that a person is in the table)
    private By nameCell(String name) {
        // TODO 4: return a By.xpath for the name cell.
        return null;
    }

    // ---------- Actions ----------

    public void selectEmployee(String name) {
        // TODO 5: find the checkbox of this employee and click it.
    }

    // Takes a LIST of names: loop and reuse selectEmployee
    public void selectEmployees(List<String> names) {
        // TODO 6: select every employee in the list.
    }

    public boolean isSelected(String name) {
        // TODO 7: return true if the checkbox of this employee is ticked.
        return false;
    }

    public String getSelectedCountText() {
        return driver.findElement(selectedCount).getText();
    }

    public String getRole(String name) {
        // TODO 8: return the role text of this employee.
        return "";
    }

    public String getStatus(String name) {
        // TODO 9: return the status text of this employee.
        return "";
    }

    public void clickEdit(String name) {
        // TODO 10: click the Edit button of this employee.
    }

    public String getEditMessage() {
        return driver.findElement(editMessage).getText();
    }

    // findElements does not throw when nothing matches, so it is safe for "is it there?"
    public boolean isEmployeeListed(String name) {
        // TODO 11: return true if the name is in the table, false if it is not.
        //          Do not use findElement here. Why?
        return false;
    }
}