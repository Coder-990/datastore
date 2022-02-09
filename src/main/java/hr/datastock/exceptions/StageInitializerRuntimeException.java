package hr.datastock.exceptions;

import javafx.stage.Stage;

public class StageInitializerRuntimeException extends RuntimeException {

    public static final String ERROR_MSG =  "Error in stage initializer";
    public StageInitializerRuntimeException(Stage stage) {
        super(String.format("%s: %d", ERROR_MSG, stage));
    }

}
