package bg.sofia.uni.fmi.mjt.twitch.user;

public class DefaultUser implements User {
    private final String name;
    private UserStatus status;

    public DefaultUser(String name) {
        this.name = name;
        this.status = UserStatus.OFFLINE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UserStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
