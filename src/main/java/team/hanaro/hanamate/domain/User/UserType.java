package team.hanaro.hanamate.domain.User;
public enum UserType {
    Parent("Parent"),
    Child("Child");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
