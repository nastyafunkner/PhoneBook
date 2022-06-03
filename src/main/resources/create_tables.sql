create table country_code
(
    country varchar(50) not null
        constraint country_code_pkey
            primary key,
    code    varchar(10)
);

create table person
(
    id      bigserial not null
        constraint person_pkey
            primary key,
    name    varchar(100),
    surname varchar(100),
    country varchar(50)
        constraint fk_country
            references country_code
);

create table phone_number
(
    id        bigserial not null
        constraint phone_number_pkey
            primary key,
    number    varchar(100)
        constraint phone_number_number_key
            unique,
    person_id bigint
        constraint fk_person
            references person
            on delete cascade,
    country   varchar(50)
        constraint fk_country
            references country_code
);

