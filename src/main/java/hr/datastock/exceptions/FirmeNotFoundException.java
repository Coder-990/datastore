package hr.datastock.exceptions;

public class FirmeNotFoundException extends RuntimeException {

    public static final String ERROR_MSG =  "Could not find company";

    public FirmeNotFoundException(Long id) {
        super(String.format("%s: %d", ERROR_MSG, id));
    }
}
