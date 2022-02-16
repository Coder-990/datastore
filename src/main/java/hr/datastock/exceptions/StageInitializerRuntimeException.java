package hr.datastock.exceptions;

import java.io.IOException;

public class StageInitializerRuntimeException extends RuntimeException {

    public static final String ERROR_MSG =  "Error in stage initializer";
    public StageInitializerRuntimeException(IOException exception) {

        super(ERROR_MSG, exception.getCause());
    }


}
