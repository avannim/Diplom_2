package enums;

public enum APIEndpoints {
    //User
    CREATE_USER("/auth/register"),
    ACTIONS_USER("/auth/user"),
    LOGIN_USER("/auth/login"),

    //Order
    ACTIONS_ORDER("/orders"),
    ALL_ORDERS("/orders/all");

    private final String path;

    APIEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
