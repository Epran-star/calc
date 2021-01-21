package mycalc.exception;

public class NotAnIntegerException extends RuntimeException {

    public NotAnIntegerException() {
        super("the number here is not an integer");
    }

}

