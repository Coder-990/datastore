drop table if exists firme cascade;
create table firme
(
    IDFirme    int          not null
        primary key,
    OIBFirme   varchar(255) null,
    NazivFirme varchar(255) null
);

drop table if exists izdatnica cascade;
create table izdatnica
(
    IDIzdatnice int      not null
        primary key,
    IDFirme     int      null,
    Datum       datetime null,
    constraint izdatnica_firme__fk
        foreign key (IDFirme) references firme (IDFirme)
);

drop table if exists primka cascade;
create table primka
(
    IDPrimke int      not null
        primary key,
    IDFirme  int      null,
    Datum    datetime null,
    constraint primka_firme__fk
        foreign key (IDFirme) references firme (IDFirme)
);


drop table if exists roba cascade;
create table roba
(
    IDRobe       int          not null
        primary key,
    NazivArtikla varchar(255) null,
    Kolicina     int          null,
    Cijena       double       null,
    Opis         mediumtext   null,
    Jmj          varchar(255) null
);


drop table if exists stavkaizdatnice cascade;
create table stavkaizdatnice
(
    IDStavkaIzdatnice int not null
        primary key,
    IDIzdatnice       int null,
    IDRobe            int null,
    Kolicina          int null,
    constraint stavkaizdatnice_izdatnica__fk
        foreign key (IDIzdatnice) references izdatnica (IDIzdatnice),
    constraint stavkaizdatnice_roba__fk
        foreign key (IDRobe) references roba (IDRobe)
);

drop table if exists stavkaprimke cascade;
create table stavkaprimke
(
    IDStavkaPrimke int not null
        primary key,
    IDPrimke       int null,
    IDRobe         int null,
    Kolicina       int null,
    constraint stavkaprimke_primka__fk
        foreign key (IDPrimke) references primka (IDPrimke),
    constraint stavkaprimke_roba__fk
        foreign key (IDRobe) references roba (IDRobe)
);

drop table if exists racun cascade;
create table racun
(
    userId   varchar(255) not null
        primary key,
    password varchar(255) not null
);
