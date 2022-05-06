package hr.datastock.exceptions;

public class FirmeEntityNotFoundRuntimeException extends RuntimeException {

    public static final String ERROR_MSG = "Could not find company";

    public FirmeEntityNotFoundRuntimeException(long id) {
        super(String.format("%s: %d", ERROR_MSG, id));
    }


}
