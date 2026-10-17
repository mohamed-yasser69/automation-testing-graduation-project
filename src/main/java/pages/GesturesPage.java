package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class GesturesPage extends BasePage {

    private final Actions actions;

    private final By contextArea = By.id("contextArea");
    private final By contextResult = By.id("contextResult");
    private final By button1 = By.id("button1");
    private final By button2 = By.id("button2");
    private final By clickLog = By.id("clickLog");
    private final By gap = By.id("gap");
    private final By selectedItems = By.id("selectedItems");
    private final By usernameField = By.id("username");
    private final By loginResult = By.id("loginResult");

    public GesturesPage(WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
    }

    // ---------- 1. Right-click menu ----------

    // Dynamic locator: the menu option is a link with this text
    private By menuOption(String name) {
        // TODO 1: return a locator for the menu link with this text.
        return null;
    }

    public void chooseFromContextMenu(String option) {
        // TODO 2: (a) right-click the context area with contextClick(...).perform()
        //         (b) wait until the menu option is visible, then click it.
        //         The menu appears a moment AFTER the right-click, so do not click at once.
    }

    public String getContextResult() {
        return driver.findElement(contextResult).getText();
    }

    // ---------- 2. Pause between two clicks ----------

    public void clickButtonsWithPause(int pauseMillis) {
        // TODO 3: in ONE chain: click button 1, pause, click button 2, then perform().
    }

    public int getGapMillis() {
        // The text looks like "Gap: 1012 ms". We keep only the digits.
        String digits = driver.findElement(gap).getText().replaceAll("[^0-9]", "");
        return Integer.parseInt(digits);
    }

    public String getClickLog() {
        return driver.findElement(clickLog).getText();
    }

    // ---------- 3. Ctrl + click to select several items ----------

    private By itemByName(String name) {
        // TODO 4: return a By.xpath for the <li> with this text inside the list #itemList.
        return null;
    }

    public void clickItem(String name) {
        driver.findElement(itemByName(name)).click();
    }

    public void ctrlClickItems(List<String> names) {
        // TODO 5: hold Ctrl, click every item of the list, release Ctrl, then perform().
        //         Build the chain in a loop: start with keyDown(Keys.CONTROL).
    }

    public String getSelectedItemsText() {
        return driver.findElement(selectedItems).getText();
    }

    // ---------- 4. Keyboard-only login ----------

    public void loginWithKeyboard(String user, String pass) {
        // TODO 6: ONE chain: move to the username field, click it, type the user,
        //         press TAB, type the password, press ENTER, then perform().
        //         Use keyDown(...) and keyUp(...) for TAB and ENTER.
    }

    public String getLoginResult() {
        return driver.findElement(loginResult).getText();
    }
}