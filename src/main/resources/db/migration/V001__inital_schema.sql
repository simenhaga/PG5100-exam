create table users
(
    user_id       serial primary key,
    user_email    varchar(255) not null,
    user_password varchar(255) not null,
    user_created  timestamp    not null
);

create table authorities
(
    authority_id   serial primary key,
    authority_name varchar(255)
);

create table users_authorities
(
    user_id      bigint,
    authority_id bigint
);

create table animals
(
    animal_id       serial primary key,
    animal_name     varchar(255) not null,
    animal_type     varchar(255) not null,
    animal_age      integer,
    vaccinated      boolean not null,
    checked_in     timestamp not null,
    checked_out    timestamp
);