package hr.datastock.exceptions;

public class RobaEntityExistsRuntimeException extends RuntimeException{
    public static final String ERROR_MSG =  "Roba already exists";

    public RobaEntityExistsRuntimeException(Long id) {
        super(String.format("%s: %d", ERROR_MSG, id));
    }
}
