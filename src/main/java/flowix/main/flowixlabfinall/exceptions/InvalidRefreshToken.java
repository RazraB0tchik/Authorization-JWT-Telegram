package flowix.main.flowixlabfinall.exceptions;

public class InvalidRefreshToken extends Exception{
    public InvalidRefreshToken(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRefreshToken(String message) {
        super(message);
    }
}
