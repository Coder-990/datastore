package hr.datastock.exceptions;

public class FirmeEntityExistsRuntimeException extends RuntimeException {

    public static final String ERROR_MSG =  "Company already exists";

    public FirmeEntityExistsRuntimeException(Long id) {
        super(String.format("%s: %d", ERROR_MSG, id));
    }
}
