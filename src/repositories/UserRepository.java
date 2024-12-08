package repositories;

import entities.User;

public interface UserRepository {
    User login(String username, String password);
    void register(User user);
}
