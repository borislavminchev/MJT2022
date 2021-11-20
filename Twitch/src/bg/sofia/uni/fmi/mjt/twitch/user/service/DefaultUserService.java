package bg.sofia.uni.fmi.mjt.twitch.user.service;

import bg.sofia.uni.fmi.mjt.twitch.user.User;

import java.util.Map;

public class DefaultUserService implements UserService {
    Map<String, User> registeredUsers;

    public DefaultUserService(Map<String, User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    @Override
    public Map<String, User> getUsers() {
        return this.registeredUsers;
    }
}
