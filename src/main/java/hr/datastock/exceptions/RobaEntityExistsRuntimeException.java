package hr.datastock.exceptions;

import hr.datastock.entities.RobaEntity;

public class RobaEntityExistsRuntimeException extends RuntimeException{
    public static final String ERROR_MSG = "Article by this 'Name' already exists: ";

    public RobaEntityExistsRuntimeException(RobaEntity robaEntity) {
        super(String.format("%s: %s", ERROR_MSG, robaEntity.getNazivArtikla()));
    }
}
