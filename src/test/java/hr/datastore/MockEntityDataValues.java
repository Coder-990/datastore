package hr.datastore;

import hr.datastore.entities.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.of;

public class MockEntityDataValues {

    private MockEntityDataValues(){

    }
    private static final FirmeEntity FIRMA_PRIME_SOFTWARE = new FirmeEntity(1L, "45485474525", "Prime Software");
    private static final FirmeEntity FIRMA_TECH_FOOT = new FirmeEntity(11L, "586510002144", "TechFoot");
    private static final FirmeEntity FIRMA_KILOBYTE = new FirmeEntity(13L, "02013025652", "KiloByte");
    private static final FirmeEntity FIRMA_CYBER_TECH = new FirmeEntity(14L, "47459652365", "CyberTech");
    private static final FirmeEntity FIRMA_LUMINUM = new FirmeEntity(24L, "54526589110", "Luminum");
    private static final FirmeEntity FIRMA_KILOBYTE_TEST = new FirmeEntity(95L, "02013025652", "KiloBytetest");

    public static final String CPU_AMD = "CPU_AMD";
    private static final RobaEntity CPU_AMD_1 = new RobaEntity(1L, CPU_AMD + "x2A4_4020", 10, new BigDecimal("215.00"), "Procesor AMD", "kom");
    public static final RobaEntity CPU_AMD_2 = new RobaEntity(2L, CPU_AMD + "A6_9500", 10, new BigDecimal("393.00"), "Procesor AMD", "kom");
    public static final RobaEntity CPU_AMD_3 = new RobaEntity(3L, CPU_AMD + "x4FX_4300", 10, new BigDecimal("425.00"), "Procesor AMD", "kom");
    private static final RobaEntity CPU_AMD_4 = new RobaEntity(8L, CPU_AMD + "Rayzen5_1600X_BOX", 10, new BigDecimal("1580.00"), "Procesor AMD", "kom");
    private static final RobaEntity CPU_AMD_5 = new RobaEntity(9L, CPU_AMD + "_Rayzen7_1800X_BOX", 10, new BigDecimal("2589.99"), "Procesor AMD", "kom");
    private static final RobaEntity MBO_ASUS_1 = new RobaEntity(10L, "MBO_ASUSa68HM_K", 10, new BigDecimal("290.00"), "MaticnaPloca ASUS", "kom");
    private static final RobaEntity MBO_ASUS_2 = new RobaEntity(25L, "MBO_ASUS_M5A97_R2.0", 10, new BigDecimal("675.00"), "MaticnaPloca ASUS", "kom");
    private static final RobaEntity MBO_ASUS_3 = new RobaEntity(26L, "MBO_ASUS_M5A97_LE_R2.0", 10, new BigDecimal("475.00"), "MaticnaPloca ASUS", "kom");

    private static final LocalDate STAVKA_IZDATNICE_DATE_STORNO_1 = of(2022, 2, 21);
    private static final LocalDate STAVKA_IZDATNICE_DATE_STORNO_2 = of(2022, 4, 27);
    private static final LocalDate STAVKA_IZDATNICE_DATE_STORNO_3 = of(2022, 5, 4);
    private static final LocalDate STAVKA_IZDATNICE_DATE_STORNO_4 = of(2022, 2, 21);

    private static final LocalDate STAVKA_PRIMKE_DATE_STORNO_1 = of(2022, 2, 20);
    private static final LocalDate STAVKA_PRIMKE_DATE_STORNO_2 = of(2022, 2, 21);
    private static final LocalDate STAVKA_PRIMKE_DATE_STORNO_3 = of(2022, 2, 22);

    private static final LocalDate IZDATNICA_DATE_1 = of(2022, 2, 18);
    private static final LocalDate IZDATNICA_DATE_2 = of(2022, 2, 26);
    private static final LocalDate IZDATNICA_DATE_3 = of(2022, 3, 4);
    private static final LocalDate IZDATNICA_DATE_4 = of(2022, 4, 30);
    private static final LocalDate PRIMKA_DATE_1 = of(2022, 2, 1);
    private static final LocalDate PRIMKA_DATE_2 = of(2022, 2, 23);
    private static final LocalDate PRIMKA_DATE_3 = of(2022, 1, 4);
    private static final LocalDate PRIMKA_DATE_4 = of(2022, 2, 1);
    private static final LocalDate PRIMKA_DATE_5 = of(2022, 5, 15);
    private static final LocalDate PRIMKA_DATE_6 = of(2022, 5, 21);
    private static final IzdatnicaEntity IZDATNICA_ENTITY_1 = new IzdatnicaEntity(5L, IZDATNICA_DATE_1, FIRMA_PRIME_SOFTWARE);
    private static final IzdatnicaEntity IZDATNICA_ENTITY_2 = new IzdatnicaEntity(19L, IZDATNICA_DATE_3, FIRMA_KILOBYTE);
    private static final IzdatnicaEntity IZDATNICA_ENTITY_3 = new IzdatnicaEntity(18L, IZDATNICA_DATE_2, FIRMA_TECH_FOOT);
    private static final IzdatnicaEntity IZDATNICA_ENTITY_4 = new IzdatnicaEntity(21L, IZDATNICA_DATE_3, FIRMA_KILOBYTE);
    public static final IzdatnicaEntity IZDATNICA_ENTITY_5 = new IzdatnicaEntity(55L, IZDATNICA_DATE_4, FIRMA_CYBER_TECH);
    private static final PrimkaEntity PRIMKA_ENTITY_1 = new PrimkaEntity(2L, PRIMKA_DATE_1, FIRMA_PRIME_SOFTWARE);
    private static final PrimkaEntity PRIMKA_ENTITY_2 = new PrimkaEntity(16L, PRIMKA_DATE_2, FIRMA_TECH_FOOT);
    private static final PrimkaEntity PRIMKA_ENTITY_3 = new PrimkaEntity(17L, PRIMKA_DATE_3, FIRMA_CYBER_TECH);
    private static final PrimkaEntity PRIMKA_ENTITY_4 = new PrimkaEntity(27L, PRIMKA_DATE_4, FIRMA_LUMINUM);


    public static List<StavkaPrimkeEntity> givenStavkaPrimkeDataRecords() {
        return Arrays.asList(
                new StavkaPrimkeEntity(4L, PRIMKA_ENTITY_1, CPU_AMD_4, 7, true, STAVKA_PRIMKE_DATE_STORNO_1),
                new StavkaPrimkeEntity(31L, PRIMKA_ENTITY_2, MBO_ASUS_2, 4, true, STAVKA_PRIMKE_DATE_STORNO_2),
                new StavkaPrimkeEntity(34L, PRIMKA_ENTITY_3, MBO_ASUS_3, 4, true, STAVKA_PRIMKE_DATE_STORNO_3),
                new StavkaPrimkeEntity(3L, PRIMKA_ENTITY_4, CPU_AMD_5, 5, false, null)
        );
    }

    public static List<StavkaIzdatniceEntity> givenStavkaIzdatniceDataRecords() {
        return Arrays.asList(
                new StavkaIzdatniceEntity(7L, IZDATNICA_ENTITY_1, CPU_AMD_2, 2, true, STAVKA_IZDATNICE_DATE_STORNO_1),
                new StavkaIzdatniceEntity(66L, IZDATNICA_ENTITY_2, CPU_AMD_4, 12, true, STAVKA_IZDATNICE_DATE_STORNO_2),
                new StavkaIzdatniceEntity(104L, IZDATNICA_ENTITY_3, CPU_AMD_3, 1, true, STAVKA_IZDATNICE_DATE_STORNO_3),
                new StavkaIzdatniceEntity(21L, IZDATNICA_ENTITY_4, CPU_AMD_1, 2, true, STAVKA_IZDATNICE_DATE_STORNO_4)
        );
    }
    public static List<RacunEntity> givenRacunDataRecords() {
        return Arrays.asList(
                new RacunEntity("user","user"),
                new RacunEntity("test","test"),
                new RacunEntity("editor","editor")
        );
    }

    public static List<FirmeEntity> givenFirmeDataRecords() {
        return Arrays.asList(
                FIRMA_PRIME_SOFTWARE, FIRMA_TECH_FOOT, FIRMA_KILOBYTE,
                FIRMA_CYBER_TECH, FIRMA_LUMINUM, FIRMA_KILOBYTE_TEST
        );
    }
    public static List<RobaEntity> givenRobaDataRecords() {
        return Arrays.asList(
                CPU_AMD_1, CPU_AMD_2, CPU_AMD_3, CPU_AMD_4,
                CPU_AMD_5, MBO_ASUS_1, MBO_ASUS_2, MBO_ASUS_3
        );
    }

    public static List<IzdatnicaEntity> givenIzdatnicaDataRecords() {
        return Arrays.asList(
                IZDATNICA_ENTITY_1, IZDATNICA_ENTITY_2,
                IZDATNICA_ENTITY_3, IZDATNICA_ENTITY_4,
                IZDATNICA_ENTITY_5
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


}
