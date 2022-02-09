package hr.datastock.exceptions;

public class FirmeNotFoundRuntimeException extends RuntimeException {

    public static final String ERROR_MSG =  "Could not find company";

    public FirmeNotFoundRuntimeException(Long id) {
        super(String.format("%s: %d", ERROR_MSG, id));
    }
}
