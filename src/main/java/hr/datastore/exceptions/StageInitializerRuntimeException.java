package hr.datastore.exceptions;

import java.io.IOException;

public class StageInitializerRuntimeException extends RuntimeException {

    public static final String ERROR_MSG =  "Unable to find screen: ";
    public StageInitializerRuntimeException(IOException exception) {

        super(ERROR_MSG, exception.getCause());
    }


}
