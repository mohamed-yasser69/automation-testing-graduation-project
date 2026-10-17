package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class SingleSelectPage extends BasePage {

    private final By country = By.id("country");
    private final By result = By.id("countryResult");

    public SingleSelectPage(WebDriver driver) {
        super(driver);
    }

    // Wrap the <select> in Selenium's Select class. We use it in every method below.
    private Select dropdown() {
        // TODO 1: find the <select> element and return it wrapped in a Select object.
        return null;
    }

    public void chooseByText(String text) {
        // TODO 2: choose the option by the text the user sees.
    }

    public void chooseByValue(String value) {
        // TODO 3: choose the option by its value attribute.
    }

    public void chooseByIndex(int index) {
        // TODO 4: choose the option by its position (the first option is 0).
    }

    public String getSelectedText() {
        // TODO 5: return the text of the option that is selected now.
        return "";
    }

    public List<String> getAllOptionTexts() {
        // TODO 6: return the text of ALL options as a list (loop or stream).
        return null;
    }

    public boolean isMultiple() {
        // TODO 7: return true if this dropdown allows more than one selection.
        return true;
    }

    public String getResultMessage() {
        return driver.findElement(result).getText();
    }
}