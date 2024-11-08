package hr.datastore.exceptions;

public class RacunEntityUserIdCheckRuntimeExcpetion extends RuntimeException{
    public static final String ERROR_MSG = "Please check your user login id!";

    public RacunEntityUserIdCheckRuntimeExcpetion(String userID) {
        super(String.format("%s: %s", ERROR_MSG, userID));
    }
}
