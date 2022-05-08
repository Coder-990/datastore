package hr.datastock;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.entities.PrimkaEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.of;

public class HardcodedDataValues {

    private static final FirmeEntity FIRMA_PRIME_SOFTWARE = new FirmeEntity(1L, "45485474525", "Prime Software");
    private static final FirmeEntity FIRMA_TECH_FOOT = new FirmeEntity(11L, "586510002144", "TechFoot");
    private static final FirmeEntity FIRMA_KILOBYTE = new FirmeEntity(13L, "02013025652", "KiloByte");
    private static final FirmeEntity FIRMA_CYBER_TECH = new FirmeEntity(14L, "47459652365", "CyberTech");
    private static final FirmeEntity FIRMA_LUMINUM = new FirmeEntity(24L, "54526589110", "Luminum");

    private static final FirmeEntity FIRMA_KILOBYTE_TEST = new FirmeEntity(95L, "02013025652", "KiloBytetest");

    private static final LocalDate IZDATNICA_DATE_1 = of(2022, 2, 18);
    private static final LocalDate IZDATNICA_DATE_2 = of(2022, 2, 26);
    private static final LocalDate IZDATNICA_DATE_3 = of(2022, 3, 4);
    private static final LocalDate IZDATNICA_DATE_4 = of(2022, 4, 30);

    private static final LocalDate PRIMKA_DATE_1 = of(2022, 1, 1);
    private static final LocalDate PRIMKA_DATE_2 = of(2022, 2, 23);
    private static final LocalDate PRIMKA_DATE_3 = of(2022, 1, 4);
    private static final LocalDate PRIMKA_DATE_4 = of(2022, 2, 1);
    private static final LocalDate PRIMKA_DATE_5 = of(2022, 5, 15);
    private static final LocalDate PRIMKA_DATE_6 = of(2022, 5, 21);

    public static List<IzdatnicaEntity> givenIzdatnicaDataRecords() {
        return Arrays.asList(
                new IzdatnicaEntity(5L, IZDATNICA_DATE_1, FIRMA_PRIME_SOFTWARE),
                new IzdatnicaEntity(18L, IZDATNICA_DATE_2, FIRMA_TECH_FOOT),
                new IzdatnicaEntity(19L, IZDATNICA_DATE_3, FIRMA_KILOBYTE),
                new IzdatnicaEntity(55L, IZDATNICA_DATE_4, FIRMA_CYBER_TECH)
        );
    }

    public static List<PrimkaEntity> givenPrimkaDataRecords() {
        return Arrays.asList(
                new PrimkaEntity(2L, PRIMKA_DATE_1, FIRMA_PRIME_SOFTWARE),
                new PrimkaEntity(16L, PRIMKA_DATE_2, FIRMA_TECH_FOOT),
                new PrimkaEntity(17L, PRIMKA_DATE_3, FIRMA_CYBER_TECH),
                new PrimkaEntity(27L, PRIMKA_DATE_4, FIRMA_LUMINUM),
                new PrimkaEntity(91L, PRIMKA_DATE_5, FIRMA_KILOBYTE),
                new PrimkaEntity(99L, PRIMKA_DATE_6, FIRMA_CYBER_TECH)
        );
    }

    public static List<FirmeEntity> givenFirmeDataRecords() {
        return Arrays.asList(
                FIRMA_PRIME_SOFTWARE, FIRMA_TECH_FOOT, FIRMA_KILOBYTE,
                FIRMA_CYBER_TECH, FIRMA_LUMINUM, FIRMA_KILOBYTE_TEST
        );
    }
}
