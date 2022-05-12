package hr.datastock.exceptions;

public class RacunEntityUserPasswordCheckRuntimeExcpetion extends RuntimeException {
    public static final String ERROR_MSG = "Please enter correct password!";

    public RacunEntityUserPasswordCheckRuntimeExcpetion(String password) {
        super(String.format("%s: %s", ERROR_MSG, password));
    }
}
