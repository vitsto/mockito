package pro.sky;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    List<User> users = new LinkedList<>();

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public Optional<User> findByLogin(String login) {
        return users
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .findAny();
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return users
                .stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findAny();
    }

    public void addUser(User user) {
        users.add(user);
    }
}
