package hr.datastock.exceptions;

import hr.datastock.entities.StavkaIzdatniceEntity;

public class StavkaIzdatniceSameRobaEntityExistsRuntimeException extends RuntimeException {
    public static final String ERROR_MSG = "Shippment item by thih 'article name' already exists: ";

    public StavkaIzdatniceSameRobaEntityExistsRuntimeException(StavkaIzdatniceEntity stavkaIzdatniceEntity) {
        super(String.format("%s: %s", ERROR_MSG, stavkaIzdatniceEntity.getStavkaIzdatniceRobe().getNazivArtikla()));
    }
}
