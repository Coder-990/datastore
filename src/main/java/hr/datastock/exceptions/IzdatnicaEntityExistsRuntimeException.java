package hr.datastock.exceptions;

public class IzdatnicaEntityExistsRuntimeException extends RuntimeException {

    public static final String ERROR_MSG =  "Izdatnica already exists";

    public IzdatnicaEntityExistsRuntimeException(Long id) {
        super(String.format("%s: %d", ERROR_MSG, id));
    }
}
