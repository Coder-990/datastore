package hr.datastore.exceptions;

public class RacunEntitiyNotFoundRuntimeException extends RuntimeException {

    public static final String ERROR_MSG = "Could not find user";

    public RacunEntitiyNotFoundRuntimeException(String userId) {
        super(String.format("%s: %s", ERROR_MSG, userId));
    }


}
