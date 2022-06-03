create schema public;

comment on schema public is 'standard public schema';

alter schema public owner to postgres;

create table person
(
	id bigserial not null
		constraint person_pkey
			primary key,
	name varchar(100),
	surname varchar(100)
);

alter table person owner to postgres;

create table phone_number
(
	id bigserial not null
		constraint phone_number_pkey
			primary key,
	number varchar(100)
		constraint phone_number_number_key
			unique,
	person_id bigint
		constraint fk_person
			references person
);

alter table phone_number owner to postgres;

