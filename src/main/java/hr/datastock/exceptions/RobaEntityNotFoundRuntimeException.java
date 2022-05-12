package hr.datastock.exceptions;

public class RobaEntityNotFoundRuntimeException extends RuntimeException{
    public static final String ERROR_MSG = "Could not find article";

    public RobaEntityNotFoundRuntimeException(long id) {
        super(String.format("%s: %d", ERROR_MSG, id));
    }
}
