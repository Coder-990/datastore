USE datastore;

INSERT INTO datastore.FirmeEntity (OIBFirme, NazivFirme)
VALUES ('54758545965', 'KoderCro');
INSERT INTO datastore.FirmeEntity (OIBFirme, NazivFirme)
VALUES ('58651000214', 'TechFoot');
INSERT INTO datastore.FirmeEntity (OIBFirme, NazivFirme)
VALUES ('02013025652', 'KiloByte');
INSERT INTO datastore.FirmeEntity (OIBFirme, NazivFirme)
VALUES ('47459652365', 'CyberTech');
INSERT INTO datastore.FirmeEntity (OIBFirme, NazivFirme)
VALUES ('54526589110', 'Luminum');
INSERT INTO datastore.FirmeEntity (OIBFirme, NazivFirme)
VALUES ('45125475856', 'NewCompany');

INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('CPU_AMDx2A4_4020', 10, 215,
        'Procesor AMD Richland A4-Series X2 4020 3.20GHz 2cores,cache : 1MB L2, 65W,Soc FM2, VGA : Radeon TM HD 7480D',
        'kom');
INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('CPU_AMDA6_9500', 10, 393,
        'Codename: Bristol Ridge • Architecture: Excavator • cores: 2 • Threads: 2 • Base clock: 3.50GHz • Turbo clock: 3.80GHz • TDP: 65W or 45W, SOC AM4',
        'kom');
INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('CPU_AMDx4FX_4300', 10, 425,
        'Codename: Vishera (platform "Volan") • architecture: Piledriver • cores: 4 • Threads: 4 • Base clock: 3.80GHz • Turbo clock: 4.00GHz • TDP: 95W • Manufacturing process: 32nm • Interface: HyperTransport, 5.2GT/s • L2 cache: 4MB (2x 2MB) • L3 cache: 4MB, SOC AM3+',
        'kom');
INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('CPU_AMDx4A8_7650K', 10, 599,
        'Codename: Kaveri • Architecture: Steamroller • cores: 4 • Threads: 4 • Base clock: 3.30GHz • Turbo clock: 3.80GHz • TDP: 95W • Manufacturing process: 28nm • Interface: UMI, 5GT/s • L2 cache: 4MB (2x 2MB) • L3 cache: not available, Soc FM2+',
        'kom');
INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('CPU_AMD_a10_9700', 10, 760,
        'Codename: Bristol Ridge • architecture: Excavator • cores: 4 • Threads: 4 • Base clock: 3.50GHz • Turbo clock: 3.80GHz • TDP: 65W • Manufacturing process: 28nm • Interface: UMI • L2 cache: 2MB (2x 1MB) • L3 cache: not available, Soc AM4',
        'piece');
INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('CPU_AMDx8FX_8300', 10, 660,
        'Codename: Vishera (platform "Volan") • Architecture: Piledriver • cores: 8 • Threads: 8 • Base clock: 3.30GHz • Turbo clock: 4.20GHz • TDP: 95W • Manufacturing process: 32nm • Interface: HyperTransport, 5.2GT/s • L2 cache: 8MB (4x 2MB) • L3 cache: 8MB, Soc AM3+',
        'kom');
INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('CPU_AMDx4FX_4300', 10, 425,
        'Codename: Vishera (platform "Volan") • architecture: Piledriver • cores: 4 • Threads: 4 • Base clock: 3.80GHz • Turbo clock: 4.00GHz • TDP: 95W • Manufacturing process: 32nm • Interface: HyperTransport, 5.2GT/s • L2 cache: 4MB (2x 2MB) • L3 cache: 4MB, SOC AM3+',
        'kom');
INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('CPU_AMDRayzen5_1600X_BOX', 10, 1580,
        'Type: Hexa-Core "Summit Ridge" (Zen) • Base clock: 3.60GHz • Turbo clock: 4.00GHz • TDP: 95W • Manufacturing process: 14nm • Interface: SMI • L2 cache: 3MB (6x 512kB) • L3 cache: 16MB (2x 8MB), Soc AM4',
        'kom');
INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('CPU_AMD_Rayzen7_1800X_BOX', 10, 2589.99,
        'Type: Octa-Core "Summit Ridge" (Zen) • Base clock: 3.60GHz • Turbo clock: 4.00GHz • TDP: 95W • Manufacturing process: 14nm • Interface: SMI • L2 cache: 4MB • L3 cache: 16MB, Soc AM4',
        'pieces');
INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('MBO_ASUSa68HM_K', 10, 290,
        'ASUS A68HM-K, Soc FM2+, AMD A68H FCH (Bolton D2H), DDR3, Broj utora 2, 1866,2133 MHz, PCI 1, PCIe 3.0 x16 1, PCIe x1 1, SATA3 4, RAID 0,1,1+0,JBOD, 6-kanalni audio, 1x 10/100/1000 Ethernet, USB 3.0 2, USB 2.0 2',
        'kom');
INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('MBO_ASUSPrimeA320M_K', 10, 375, 'Opis
Form factor: µATX • Chipset: AMD A320 • RAM: 2x DDR4 DIMM, dual PC4-25600U/?DDR4-3200 (OC), max. 32GB (UDIMM) • Extension slots: 1x PCIe 3.0 x16, 2x PCIe 2.0 x1, 1x M.2/?M-Key (PCIe 3.0 x4/?SATA, 2280/?2260/?2242) • External connectors: 1x VGA, 1x HDMI 1.4, 4x USB-A 3.0, 2x USB-A 2.0, 1x Gb LAN (Realtek RTL8111H), 3x jack, 1x PS/?2 keyboard, 1x PS/?2 mouse • Internal connectors: 2x USB 3.0, 4x USB 2.0, 4x SATA 6Gb/s (A320), 1x serial • Header Cooling: 1x CPU fan 4-Pin, 1x fan 4-Pin • Header Lighting: not available • Audio: 7.1 (Realtek ALC887) • RAID level: 0/?1/?10 (A320) • Multi-GPU: not available • power connections: 1x 24-Pin ATX, 1x 4-Pin ATX12V • CPU-phases: 6 • Graphics: IGP (via CPU/?APU) • Lighting: not available • Special features: audio+solid capacitors, Soc AM4',
        'pieces');
INSERT INTO datastore.RobaEntity(NazivArtikla, Kolicina, Cijena, Opis, Jmj)
VALUES ('MBO_ASUS_M5A97_R2.0', 10, 675, 'Opis
Form factor: µATX • Chipset: AMD A320 • RAM: 2x DDR4 DIMM, dual PC4-25600U/?DDR4-3200 (OC), max. 32GB (UDIMM) • Extension slots: 1x PCIe 3.0 x16, 2x PCIe 2.0 x1, 1x M.2/?M-Key (PCIe 3.0 x4/?SATA, 2280/?2260/?2242) • External connectors: 1x VGA, 1x HDMI 1.4, 4x USB-A 3.0, 2x USB-A 2.0, 1x Gb LAN (Realtek RTL8111H), 3x jack, 1x PS/?2 keyboard, 1x PS/?2 mouse • Internal connectors: 2x USB 3.0, 4x USB 2.0, 4x SATA 6Gb/s (A320), 1x serial • Header Cooling: 1x CPU fan 4-Pin, 1x fan 4-Pin • Header Lighting: not available • Audio: 7.1 (Realtek ALC887) • RAID level: 0/?1/?10 (A320) • Multi-GPU: not available • power connections: 1x 24-Pin ATX, 1x 4-Pin ATX12V • CPU-phases: 6 • Graphics: IGP (via CPU/?APU) • Lighting: not available • Special features: audio+solid capacitors, Soc AM4',
        'kom');

INSERT INTO datastore.IzdatnicaEntity (IDFirme, Datum)
VALUES (1, '2022-02-18 00:00:00');
INSERT INTO datastore.IzdatnicaEntity (IDFirme, Datum)
VALUES (3, '2022-02-26 00:00:00');
INSERT INTO datastore.IzdatnicaEntity (IDFirme, Datum)
VALUES (5, '2022-03-04 00:00:00');

INSERT INTO datastore.PrimkaEntity (IDFirme, Datum)
VALUES (2, '2022-02-01 00:00:00');
INSERT INTO datastore.PrimkaEntity (IDFirme, Datum)
VALUES (4, '2022-02-23 00:00:00');
INSERT INTO datastore.PrimkaEntity (IDFirme, Datum)
VALUES (6, '2022-01-04 00:00:00');
INSERT INTO datastore.PrimkaEntity (IDFirme, Datum)
VALUES (1, '2022-02-01 00:00:00');
INSERT INTO datastore.PrimkaEntity (IDFirme, Datum)
VALUES (3, '2022-02-18 00:00:00');

INSERT INTO datastore.StavkaIzdatniceEntity (IDIzdatnice, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (1, 2, 2, '2022-02-21', true);
INSERT INTO datastore.StavkaIzdatniceEntity (IDIzdatnice, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (2, 8, 4, null, false);
INSERT INTO datastore.StavkaIzdatniceEntity (IDIzdatnice, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (3, 8, 4, '2022-02-21', true);
INSERT INTO datastore.StavkaIzdatniceEntity (IDIzdatnice, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (1, 7, 4, '2022-02-21', true);
INSERT INTO datastore.StavkaIzdatniceEntity (IDIzdatnice, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (2, 1, 2, '2022-02-21', true);
INSERT INTO datastore.StavkaIzdatniceEntity (IDIzdatnice, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (3, 6, 2, '2022-02-21', true);
INSERT INTO datastore.StavkaIzdatniceEntity (IDIzdatnice, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (1, 4, 2, null, false);

INSERT INTO datastore.StavkaPrimkeEntity (IDPrimke, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (1, 7, 5, null, false);
INSERT INTO datastore.StavkaPrimkeEntity (IDPrimke, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (2, 9, 7, '2022-02-20', true);
INSERT INTO datastore.StavkaPrimkeEntity (IDPrimke, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (3, 2, 1, null, false);
INSERT INTO datastore.StavkaPrimkeEntity (IDPrimke, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (4, 4, 4, null, false);
INSERT INTO datastore.StavkaPrimkeEntity (IDPrimke, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (5, 4, 4, '2022-02-21', true);
INSERT INTO datastore.StavkaPrimkeEntity (IDPrimke, IDRobe, Kolicina, DatumStorno, Storno)
VALUES (2, 5, 4, '2022-02-22', true);

INSERT INTO datastore.RacunEntity (userId, password)
VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3');
