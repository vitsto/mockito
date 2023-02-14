package pro.sky;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> getAllLogins() {
        return userRepository.getUsers()
                .stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
    }

    public void createUser(String login, String password) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login and password must be entered");
        }
        if (userRepository.findByLogin(login).isPresent()) {
            throw new UserNonUniqueException("User with this login has already been created");
        }
        userRepository.addUser(new User(login, password));
    }

    public boolean authorize(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password).isPresent();
    }
}
