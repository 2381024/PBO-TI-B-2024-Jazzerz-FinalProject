package services;

import entities.User;

public interface UserService {
    User login(String username, String password);
    void register(User user);
}
