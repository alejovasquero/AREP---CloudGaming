package edu.eci.arep;

public class GameException extends Exception{

    public static String INVALID_BOARD = "The board dimensions are not valid";
    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }
}
