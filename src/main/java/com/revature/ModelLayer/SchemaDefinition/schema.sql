drop schema if exists project_one
cascade;
create schema project_one authorization postgres;
set search_path
to project_one;

create table ers_reimbursment_status
(
    reimb_status_id serial primary key,
    reimb_status text not null unique
);
create table ers_reimbursment_type
(
    reimb_type_id serial primary key,
    remb_type text
);
create table ers_user_roles
(
    ers_user_role_id serial primary key,
    user_role text not null unique
);

insert into ers_user_roles
    (ers_user_role_id, user_role)
values
    (default, 'employee'),
    (default, 'finance manager');
insert into ers_reimbursment_type
    (reimb_type_id,remb_type)
values(default, 'LODGING'),
    (default, 'TRAVEL'),
    (default, 'FOOD'),
    (default, 'OTHER');
insert into ers_reimbursment_status
    (reimb_status_id,reimb_status)
values
    (default, 'PENDING'),
    (default, 'APPROVED'),
    (default, 'DENIED');


select *
from ers_user_roles;
select *
from ers_reimbursment_type;
select *
from ers_reimbursment_status;

create table ers_users
(
    ers_users_id serial primary key,
    ers_username text not null unique,
    ers_password text not null,
    user_first_name text,
    user_last_name text,
    user_email text not null unique,
    user_role_id serial not null,
    constraint user_roles_fk foreign key (user_role_id) 
			 references ers_user_roles(ers_user_role_id)
);


create table ers_reimbursment
(
    remb_id serial primary key,
    reimb_amount numeric(12,2),
    reimb_submitted timestamp,
    reimb_resolved timestamp,
    reimb_description text,
    reimb_receipt bytea,
    reimb_author serial not null,
    reimb_resolver serial not null,
    reimb_status_id serial not null,
    reimb_type_id serial not null,
    constraint ers_users_fk_auth foreign key(reimb_author)
			 references ers_users(ers_users_id),
    constraint ers_users_fk_reslvr foreign key(reimb_resolver)
			 references ers_users(ers_users_id),
    constraint ers_reimbursment_status_fk foreign key(reimb_status_id)
			 references ers_reimbursment_status(reimb_status_id),
    constraint ers_reimbursement_type_fk foreign key(reimb_type_id)
			 references ers_reimbursment_type(reimb_type_id)
);