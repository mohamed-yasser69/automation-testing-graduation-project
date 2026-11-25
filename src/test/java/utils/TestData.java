package utils;

/** Plain records the JSON test data is mapped onto by Jackson. */
public final class TestData {

    private TestData() {
    }

    public record Credentials(String username, String password) {
    }

    public record InvalidLogin(String username, String password, String expectedError) {
    }

    public record NameData(String name) {
    }

    public record NewEmployee(String firstName, String lastName) {
    }
}
