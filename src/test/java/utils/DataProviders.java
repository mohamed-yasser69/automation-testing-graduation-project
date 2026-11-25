package utils;

import org.testng.annotations.DataProvider;
import utils.TestData.*;

import java.util.List;

/** TestNG DataProviders - every value comes from testdata.json, none are hardcoded in tests. */
public final class DataProviders {

    private DataProviders() {
    }

    private static <T> Object[][] toRows(List<T> items) {
        Object[][] rows = new Object[items.size()][1];
        for (int i = 0; i < items.size(); i++) {
            rows[i][0] = items.get(i);
        }
        return rows;
    }

    @DataProvider(name = "validUser")
    public static Object[][] validUser() {
        return toRows(JsonUtils.readList("validUser", Credentials.class));
    }

    @DataProvider(name = "invalidLogins")
    public static Object[][] invalidLogins() {
        return toRows(JsonUtils.readList("invalidLogins", InvalidLogin.class));
    }

    @DataProvider(name = "existingEmployees")
    public static Object[][] existingEmployees() {
        return toRows(JsonUtils.readList("existingEmployees", NameData.class));
    }

    @DataProvider(name = "nonExistingEmployees")
    public static Object[][] nonExistingEmployees() {
        return toRows(JsonUtils.readList("nonExistingEmployees", NameData.class));
    }

    @DataProvider(name = "newEmployees")
    public static Object[][] newEmployees() {
        return toRows(JsonUtils.readList("newEmployees", NewEmployee.class));
    }

    /** One row holding the whole expected menu list. */
    @DataProvider(name = "sidebarMenus")
    public static Object[][] sidebarMenus() {
        List<String> names = JsonUtils.readList("sidebarMenus", NameData.class).stream().map(NameData::name).toList();
        return new Object[][]{{names}};
    }
}
