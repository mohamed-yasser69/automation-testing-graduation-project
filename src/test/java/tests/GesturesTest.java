package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.GesturesPage;

import java.util.List;

public class GesturesTest extends BaseTest {

    private GesturesPage page;

    @BeforeMethod
    public void openGesturesPage() {
        openLab("lab6");
        page = new GesturesPage(driver);
    }

    @DataProvider(name = "menuOptions")
    public Object[][] menuOptions() {
        return new Object[][]{{"Copy"}, {"Paste"}, {"Delete"}};
    }

    @DataProvider(name = "pauses")
    public Object[][] pauses() {
        return new Object[][]{{500}, {1500}};
    }

    // Each row holds a LIST of items (in the same order as the page)
    @DataProvider(name = "itemSets")
    public Object[][] itemSets() {
        return new Object[][]{
                {List.of("Item 1", "Item 3")},
                {List.of("Item 2", "Item 4", "Item 5")}
        };
    }

    @DataProvider(name = "users")
    public Object[][] users() {
        return new Object[][]{{"testuser", "secret123"}, {"amr", "Pass#1"}};
    }

    @Test(dataProvider = "menuOptions")
    public void contextMenuOptionWorks(String option) {
        page.chooseFromContextMenu(option);

        Assert.assertEquals(page.getContextResult(), "Action: " + option, "Result of the context menu");
    }

    @Test(dataProvider = "pauses")
    public void pauseCreatesAGapBetweenClicks(int pauseMillis) {
        page.clickButtonsWithPause(pauseMillis);

        Assert.assertEquals(page.getClickLog(), "Button 1 then Button 2 clicked", "Both buttons should be clicked");
        Assert.assertTrue(page.getGapMillis() >= pauseMillis,
                "Gap should be at least " + pauseMillis + " ms but was " + page.getGapMillis());
    }

    @Test
    public void normalClickSelectsOnlyOneItem() {
        page.clickItem("Item 1");
        page.clickItem("Item 2");

        Assert.assertEquals(page.getSelectedItemsText(), "Selected: Item 2", "Only the last click stays selected");
    }

    @Test(dataProvider = "itemSets")
    public void ctrlClickSelectsSeveralItems(List<String> items) {
        page.ctrlClickItems(items);

        Assert.assertEquals(page.getSelectedItemsText(), "Selected: " + String.join(", ", items),
                "Items selected with Ctrl");
    }

    @Test(dataProvider = "users")
    public void chainedKeyboardLogin(String user, String pass) {
        page.loginWithKeyboard(user, pass);

        Assert.assertEquals(page.getLoginResult(), "Logged in as " + user, "Result of the keyboard login");
    }
}