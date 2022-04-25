insert into users
values (nextval('users_user_id_seq'), 'admin@admin.com', '$2a$12$aJZigio5thHN4luSrVcQ3enLErI74CDaHqHANgoW69B8ru5uOUCrW', now());
values (nextval('users_user_id_seq'), 'user@user.com', '$2a$12$aJZigio5thHN4luSrVcQ3enLErI74CDaHqHANgoW69B8ru5uOUCrW', now());

insert into authorities
values (nextval('authorities_authority_id_seq'), 'USER');

insert into authorities
values (nextval('authorities_authority_id_seq'), 'ADMIN');

insert into users_authorities
values (1, 2);

insert into users_authorities
values (2, 1);

insert into animals
values (1, 'Raja', 'Dog', 13, true, now(), null);

insert into animals
values (2, 'NeckPain', 'Sjiraff', 23, false, now(), '2021-11-23T11:23:44');
