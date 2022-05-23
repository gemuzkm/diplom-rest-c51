create table devices (
    id bigint not null auto_increment,
    created_date datetime(6),
    status varchar(255),
    last_modified_date datetime(6),
    description varchar(255),
    firmware_version varchar(255),
    ip_address varchar(255),
    mac_address varchar(255),
    model varchar(255) not null,
    serial_number varchar(255),
    type_device varchar(255) not null,
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table parameter (
    id bigint not null auto_increment,
    created_date datetime(6),
    type varchar(255),
    device_id bigint,
    primary key (id)) engine=InnoDB;

create table parameter_values (
    id bigint not null auto_increment,
    created_date datetime(6),
    value double precision,
    parameter_id bigint,
    primary key (id)) engine=InnoDB;

create table roles (
    id bigint not null auto_increment,
    created_date datetime(6),
    status varchar(255),
    last_modified_date datetime(6),
    name varchar(255),
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table users (
    id bigint not null auto_increment,
    created_date datetime(6),
    status varchar(255),
    last_modified_date datetime(6),
    email varchar(255),
    first_name varchar(25),
    last_name varchar(25),
    password varchar(255),
    phone varchar(255),
    username varchar(25),
    primary key (id)) engine=InnoDB;

alter table devices
    add constraint UK_mac_address_devices
        unique (mac_address);

alter table devices
    add constraint UK_serial_number_devices
        unique (serial_number);

alter table devices
    add constraint devices_users_fk foreign key (user_id) references users (id);

alter table parameter
    add constraint parameter_device_id_fk foreign key (device_id) references devices (id);

alter table parameter_values
    add constraint parameter_values_parameter_id_fk foreign key (parameter_id) references parameter (id);

alter table roles
    add constraint roles_user_id_fk foreign key (user_id) references users (id);
