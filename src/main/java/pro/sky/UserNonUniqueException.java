package pro.sky;

public class UserNonUniqueException extends RuntimeException{
    public UserNonUniqueException(String message) {
        super(message);
    }
}
