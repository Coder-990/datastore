package hr.datastock;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.IzdatnicaEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.of;

public class HardcodedDataValues {

    public static final FirmeEntity FIRMA_PRIME_SOFTWARE = new FirmeEntity(1L, "45485474525", "Prime Software");
    public static final FirmeEntity FIRMA_TECH_FOOT = new FirmeEntity(11L, "586510002144", "TechFoot");
    public static final FirmeEntity FIRMA_KILO_BYTE = new FirmeEntity(13L, "02013025652", "KiloByte");
    public static final FirmeEntity FIRMA_CYBER_TECH = new FirmeEntity(14L, "47459652365", "CyberTech");
    public static final FirmeEntity FIRMA_LUMINUM = new FirmeEntity(24L, "54526589110", "Luminum");
    public static final FirmeEntity FIRMA_KILOBYTE_TEST = new FirmeEntity(95L, "02013025652", "KiloBytetest");

    public static final LocalDate IZDATNICA_DATE_1 = of(2022, 2, 18);
    public static final LocalDate IZDATNICA_DATE_2 = of(2022, 2, 26);
    public static final LocalDate IZDATNICA_DATE_3 = of(2022, 3, 4);
    public static final LocalDate IZDATNICA_DATE_4 = of(2022, 4, 30);

    public static List<IzdatnicaEntity> givenIzdatnicaDataRecords() {
        return Arrays.asList(
                new IzdatnicaEntity(5L, IZDATNICA_DATE_1, FIRMA_PRIME_SOFTWARE),
                new IzdatnicaEntity(18L, IZDATNICA_DATE_2, FIRMA_TECH_FOOT),
                new IzdatnicaEntity(19L, IZDATNICA_DATE_3, FIRMA_KILO_BYTE),
                new IzdatnicaEntity(55L, IZDATNICA_DATE_4, FIRMA_CYBER_TECH)
        );
    }

    public static List<FirmeEntity> givenFirmeDataRecords() {
        return Arrays.asList(
                FIRMA_PRIME_SOFTWARE,
                FIRMA_TECH_FOOT,
                FIRMA_KILO_BYTE,
                FIRMA_CYBER_TECH,
                FIRMA_LUMINUM,
                FIRMA_KILOBYTE_TEST
        );
    }
}
