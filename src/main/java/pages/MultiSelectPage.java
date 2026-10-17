package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class MultiSelectPage extends BasePage {

    private final By skills = By.id("skills");
    private final By showButton = By.id("showSkills");
    private final By result = By.id("skillsResult");

    public MultiSelectPage(WebDriver driver) {
        super(driver);
    }

    private Select dropdown() {
        // TODO 1: return the <select> wrapped in a Select object.
        return null;
    }

    public boolean isMultiple() {
        // TODO 2: return true if the dropdown allows more than one selection.
        return false;
    }

    // Takes a LIST of skills: choose every one of them
    public void selectSkills(List<String> skillNames) {
        // TODO 3: loop over the list and select each skill by its visible text.
        //         With a multi-select, each call ADDS to the selection.
    }

    public void deselectSkill(String skill) {
        // TODO 4: remove one skill from the selection.
    }

    public void deselectAll() {
        // TODO 5: clear the whole selection.
    }

    public List<String> getSelectedSkills() {
        // TODO 6: return the text of ALL selected options as a list.
        //         Careful: getFirstSelectedOption() only gives ONE option.
        return null;
    }

    public void clickShowSkills() {
        driver.findElement(showButton).click();
    }

    public String getResultText() {
        return driver.findElement(result).getText();
    }
}