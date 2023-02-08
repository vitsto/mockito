package pro.sky;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("When user repository is empty then method must return the empty list")
    void getEmptyListOfLogins() {
        when(userRepository.getUsers()).thenReturn(new LinkedList<>());
        assertThat(userService.getAllLogins()).isEmpty();
    }

    @Test
    @DisplayName("When user repository is filled then method must return all logins users")
    void getFilledListOfLogins() {
        when(userRepository.getUsers()).thenReturn(List.of(new User("test", "pass"),
                new User("test1", "pass1"),
                new User("test2", "pass2")));
        assertThat(userService.getAllLogins()).hasSameElementsAs(List.of("test", "test1", "test2"));
    }

    @Test
    @DisplayName("When user is created with null login then method must throw the IllegalArgumentException")
    void createUserWithNullLogin() {
        assertThatThrownBy(() -> userService.createUser(null, "pass1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Login and password must be entered");
    }

    @Test
    @DisplayName("When user is created with blank login then method must throw the IllegalArgumentException")
    void createUserWithBlankLogin() {
        assertThatThrownBy(() -> userService.createUser("", "pass1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Login and password must be entered");
    }

    @Test
    @DisplayName("When user is created with existing login then method must throw the UserNonUniqueException")
    void createUserWithExistingLogin() {
        when(userRepository.findByLogin("test")).thenReturn(Optional.of(new User("test", "pass")));
        assertThatThrownBy(() -> userService.createUser("test", "pass1"))
                .isInstanceOf(UserNonUniqueException.class)
                .hasMessage("User with this login has already been created");
    }

//    @Test
//    @DisplayName("When new user is created with correct data then method must add it to repository")
//    void createUser() {
//        //не могу понять как протестировать добавление пользователя, которое выполняется корректно.
//        // помогите плиз
//    }

    @Test
    @DisplayName("When user tries authorize with wrong data then method must return false")
    void authorizeWithWrongData() {
        when(userRepository.findByLoginAndPassword("test", "pass")).thenReturn(Optional.empty());
        assertThat(userService.authorize("test", "pass")).isFalse();
    }

    @Test
    @DisplayName("When user tries authorize with correct data then method must return true")
    void authorizeWithCorrectData() {
        when(userRepository.findByLoginAndPassword("test", "pass")).thenReturn(Optional.of(new User("test", "pass")));
        assertThat(userService.authorize("test", "pass")).isTrue();
    }
}
