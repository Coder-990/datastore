package hr.datastock.exceptions;

public class StornoStavkeIzdatnicaEntityExistsRuntimeException extends RuntimeException {

    public static final String ERROR_MSG =  "NO such id";

    public StornoStavkeIzdatnicaEntityExistsRuntimeException(Long id) {
        super(String.format("%s: %d", ERROR_MSG, id));
    }
}
