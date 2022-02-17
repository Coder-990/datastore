package hr.datastock.exceptions;

public class PrimkaEntityExistsRuntimeException extends RuntimeException {

    public static final String ERROR_MSG =  "Izdatnica already exists";

    public PrimkaEntityExistsRuntimeException(Long id) {
        super(String.format("%s: %d", ERROR_MSG, id));
    }
}
