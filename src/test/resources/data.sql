DROP TABLE IF EXISTS PUSH_MEMBER;

-- create table MEMBER_CHILD
-- (
--     id           bigint,
--     name         varchar(1000),
--     primary key (name),
-- );
--     foreign key (id) REFERENCES PUSH_MEMBER(id)
create table PUSH_MEMBER
(
    id           bigint,
    name         varchar(1000) not null,
    success_push bool default false,
    primary key (id)
);

insert into PUSH_MEMBER(id, name, success_push)
values (0, 'admin', true);