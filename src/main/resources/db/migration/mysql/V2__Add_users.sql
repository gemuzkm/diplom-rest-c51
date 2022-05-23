insert into users (created_date, status, last_modified_date, email, first_name, last_name, password, phone, username)
    values ('2022-05-10 10:40:03.969018', 'ACTIVE', '2022-05-10 10:40:03.969018', 'user@gmail.com', 'firstNameUser', 'lastNameUser', '$2a$10$OaKhs0PxNTN0PXjAnAd2.OJ45TvJAsDgIDM/2V96z3lPuKW37wSzu', '375297777777', 'user');

insert into roles (created_date, status, last_modified_date, name, user_id)
    values ('2022-05-10 10:40:03.969018', 'ACTIVE', '2022-05-10 10:40:03.969018', 'USER', 1);

insert into users (created_date, status, last_modified_date, email, first_name, last_name, password, phone, username)
    values ('2022-05-10 10:40:03.969018', 'ACTIVE', '2022-05-10 10:40:03.969018', 'admin@gmail.com', 'firstNameAdmin', 'lastNameAdmin', '$2a$10$OaKhs0PxNTN0PXjAnAd2.OJ45TvJAsDgIDM/2V96z3lPuKW37wSzu', '375298888888', 'admin');

insert into roles (created_date, status, last_modified_date, name, user_id)
    values ('2022-05-10 10:40:03.969018', 'ACTIVE', '2022-05-10 10:40:03.969018', 'ADMIN', 2);