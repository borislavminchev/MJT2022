package bg.sofia.uni.fmi.mjt.twitch.user;

import bg.sofia.uni.fmi.mjt.twitch.user.service.UserService;

public class DefaultUser implements User {
    private String name;
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
