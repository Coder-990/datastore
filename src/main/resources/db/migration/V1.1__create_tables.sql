USE datastore;

CREATE TABLE FirmeEntity (
                             IDFirme INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                             OIBFirme VARCHAR(11) UNIQUE NULL,
                             NazivFirme VARCHAR(255) NULL
);

CREATE TABLE IzdatnicaEntity (
                                 IDIzdatnice INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                 IDFirme INT NOT NULL,
                                 Datum DATETIME NULL,
                                 CONSTRAINT fk_IzdatnicaEntity_FirmeEntity FOREIGN KEY (IDFirme) REFERENCES FirmeEntity (IDFirme) ON DELETE CASCADE
);

CREATE TABLE PrimkaEntity (
                              IDPrimke INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              IDFirme INT NOT NULL,
                              Datum DATETIME NULL,
                              CONSTRAINT fk_PrimkaEntity_FirmeEntity FOREIGN KEY (IDFirme) REFERENCES FirmeEntity (IDFirme) ON DELETE CASCADE
);

CREATE TABLE RobaEntity (
                            IDRobe INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            NazivArtikla VARCHAR(255) NOT NULL,
                            Kolicina INT DEFAULT 0,
                            Cijena DECIMAL(10, 2) NULL,
                            Opis MEDIUMTEXT NULL,
                            Jmj VARCHAR(255) NULL
);

CREATE TABLE StavkaIzdatniceEntity (
                                       IDStavkaIzdatnice INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       IDIzdatnice INT NOT NULL,
                                       IDRobe INT NOT NULL,
                                       Kolicina INT DEFAULT 0,
                                       Storno BOOLEAN DEFAULT FALSE,
                                       DatumStorno DATETIME NULL,
                                       CONSTRAINT fk_StavkaIzdatniceEntity_IzdatnicaEntity FOREIGN KEY (IDIzdatnice) REFERENCES IzdatnicaEntity (IDIzdatnice) ON DELETE CASCADE,
                                       CONSTRAINT fk_StavkaIzdatniceEntity_RobaEntity FOREIGN KEY (IDRobe) REFERENCES RobaEntity (IDRobe) ON DELETE CASCADE
);

CREATE TABLE StavkaPrimkeEntity (
                                    IDStavkaPrimke INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    IDPrimke INT NOT NULL,
                                    IDRobe INT NOT NULL,
                                    Kolicina INT DEFAULT 0,
                                    Storno BOOLEAN DEFAULT FALSE,
                                    DatumStorno DATETIME NULL,
                                    CONSTRAINT fk_StavkaPrimkeEntity_PrimkaEntity FOREIGN KEY (IDPrimke) REFERENCES PrimkaEntity (IDPrimke) ON DELETE CASCADE,
                                    CONSTRAINT fk_StavkaPrimkeEntity_RobaEntity FOREIGN KEY (IDRobe) REFERENCES RobaEntity (IDRobe) ON DELETE CASCADE
);

CREATE TABLE RacunEntity (
                             UserId VARCHAR(255) NOT NULL PRIMARY KEY,
                             Password VARCHAR(255) NOT NULL
);
