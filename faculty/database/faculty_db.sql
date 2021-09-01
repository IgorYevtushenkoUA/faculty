create table role
(
    id   serial
        constraint role_pk
            primary key,
    name varchar(32)
);

alter table role
    owner to postgres;

create unique index role_id_uindex
    on role (id);

create table "user"
(
    id          serial
        constraint user_pk
            primary key,
    first_name  varchar(32)  not null,
    second_name varchar(32)  not null,
    last_name   varchar(32)  not null,
    email       varchar(128) not null,
    password    varchar(32)  not null,
    role_id     integer
        constraint user_role_id_fk
            references role
            on update cascade on delete cascade
);

alter table "user"
    owner to postgres;

create unique index user_id_uindex
    on "user" (id);

create table status
(
    id   serial
        constraint status_pk
            primary key,
    name varchar(32) not null
);

alter table status
    owner to postgres;

create unique index status_id_uindex
    on status (id);

create table student
(
    id         integer not null
        constraint student_pk
            primary key
        constraint student_user_id_fk
            references "user"
            on update cascade on delete cascade,
    course_num integer not null,
    enable     boolean not null
);

alter table student
    owner to postgres;

create table teacher
(
    id integer not null
        constraint teacher_pk
            primary key
        constraint teacher_user_id_fk
            references "user"
            on update cascade on delete cascade
);

alter table teacher
    owner to postgres;

create table topic
(
    id   serial
        constraint topic_pk
            primary key,
    name varchar(128) not null
);

alter table topic
    owner to postgres;

create unique index topic_id_uindex
    on topic (id);

create table course
(
    id                serial
        constraint course_pk
            primary key,
    topic_id          integer       not null,
    capacity          integer       not null,
    semester_start    integer       not null,
    semester_duration integer       not null,
    description       varchar(1024) not null,
    teacher_id        integer       not null
        constraint course_teacher_id_fk
            references teacher
            on update cascade on delete cascade,
    name              varchar(128)
);

alter table course
    owner to postgres;

create unique index course_id_uindex
    on course (id);

create table student_has_course
(
    student_id integer not null
        constraint student_has_course_student_id_fk
            references student
            on update cascade on delete cascade,
    course_id  integer not null
        constraint student_has_course_course_id_fk
            references course
            on update cascade on delete cascade,
    status_id  integer
        constraint student_has_course_status_id_fk
            references status
            on update cascade on delete cascade,
    mark       integer,
    constraint student_has_course_pk
        primary key (course_id, student_id)
);

alter table student_has_course
    owner to postgres;

