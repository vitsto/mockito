package pro.sky;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryTest {
    @Test
    @DisplayName("When user list is empty then method must return the empty list")
    void getEmptyList() {
        UserRepository testRepository = new UserRepository();
        List<User> actualUserList = testRepository.getUsers();
        Assertions.assertTrue(actualUserList.isEmpty());
    }

    @Test
    @DisplayName("When user list is not empty then method must return the filled list")
    void getFilledList() {
        UserRepository testRepository = new UserRepository();
        List<User> testUsersList = new LinkedList<>(List.of(
                new User("test", "test"),
                new User("test1", "test1"),
                new User("test2", "test2")));
        testUsersList.forEach(testRepository::addUser);
        Assertions.assertEquals(testUsersList, testRepository.getUsers());
    }

    @Test
    @DisplayName("When user's login exists then method must return Optional<User> contains this user")
    void getExistingUserByLogin() {
        UserRepository testRepository = new UserRepository();
        List<User> testUsersList = new LinkedList<>(List.of(
                new User("test", "test"),
                new User("test1", "test1"),
                new User("test2", "test2")));
        testUsersList.forEach(testRepository::addUser);
        Optional<User> actualUser = testRepository.findByLogin("test1");
        Assertions.assertAll(
                () -> Assertions.assertTrue(actualUser.isPresent()),
                () -> Assertions.assertEquals(actualUser.get(), testUsersList.get(1))
        );
    }

    @Test
    @DisplayName("When user's login not exists then method must return empty Optional<User>")
    void getNotExistingUserByLogin() {
        UserRepository testRepository = new UserRepository();
        List<User> testUsersList = new LinkedList<>(List.of(
                new User("test", "test"),
                new User("test1", "test1"),
                new User("test2", "test2")));
        testUsersList.forEach(testRepository::addUser);
        Optional<User> actualUser = testRepository.findByLogin("test3");
        Assertions.assertTrue(actualUser.isEmpty());
    }

    @Test
    @DisplayName("When user's login and password exist then method must return Optional<User> contains this user")
    void getExistingUserByLoginAndPassword() {
        UserRepository testRepository = new UserRepository();
        List<User> testUsersList = new LinkedList<>(List.of(
                new User("test", "test"),
                new User("test1", "test1"),
                new User("test2", "test2")));
        testUsersList.forEach(testRepository::addUser);
        Optional<User> actualUser = testRepository.findByLoginAndPassword("test1", "test1");
        Assertions.assertAll(
                () -> Assertions.assertTrue(actualUser.isPresent()),
                () -> Assertions.assertEquals(actualUser.get(), testUsersList.get(1))
        );
    }
    @Test
    @DisplayName("When user's login is wrong, but the password is correct then method must return Optional<User> contains empty list")
    void getExistingUserByWrongLoginAndPassword() {
        UserRepository testRepository = new UserRepository();
        List<User> testUsersList = new LinkedList<>(List.of(
                new User("test", "test"),
                new User("test1", "test1"),
                new User("test2", "test2")));
        testUsersList.forEach(testRepository::addUser);
        Optional<User> actualUser = testRepository.findByLoginAndPassword("test", "test1");
        Assertions.assertTrue(actualUser.isEmpty());
    }

    @Test
    @DisplayName("When user's login exist, but the password is wrong then method must return Optional<User> contains empty list")
    void getExistingUserByLoginAndWrongPassword() {
        UserRepository testRepository = new UserRepository();
        List<User> testUsersList = new LinkedList<>(List.of(
                new User("test", "test"),
                new User("test1", "test1"),
                new User("test2", "test2")));
        testUsersList.forEach(testRepository::addUser);
        Optional<User> actualUser = testRepository.findByLoginAndPassword("test1", "test");
        Assertions.assertTrue(actualUser.isEmpty());
    }
}
