package hr.datastock.exceptions;

import java.io.IOException;

public class StageInitializerRuntimeException extends RuntimeException {

    public static final String ERROR_MSG =  "Error in stage initializer";
    public StageInitializerRuntimeException(IOException stage) {

        super(ERROR_MSG, stage.getCause());
    }


}
