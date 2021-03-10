    create database IF NOT EXISTS mainichishinpo;
    use mainichishinpo;

    create table user(
    id bigint not null primary key auto_increment,
    email varchar(255) not null,
    fullname varchar(255),
    password varchar(255),
    verificationCode varchar(64),
    enable boolean
    );
    create table role(
    id bigint not null primary key auto_increment,
    name varchar(255)
    );
    create table users_roles(
    id bigint not null primary key auto_increment,
    user_id bigint not null,
    role_id bigint not null
    );

    create table comment(
        id bigint not null primary key auto_increment,
        content varchar(255),
        post_id bigint not null,
        user_id bigint not null
    );


    create table post(
        id bigint not null primary key auto_increment,
        title varchar(255),
        content text,
        user_id bigint not null,
        create_at datetime
    );

    create table tag(
        id bigint not null primary key auto_increment,
        name varchar(255)
    );

    create table post_tag(
    id bigint not null primary key auto_increment,
    tag_id bigint not null,
    post_id bigint not null
    );

    alter table users_roles add foreign key(user_id) references user(id);
    alter table users_roles add foreign key(role_id) references role(id);
    alter table comment add foreign key(post_id) references post(id);
    alter table comment add foreign key(user_id) references user(id);

    alter table post add  foreign key (user_id) references user (id);

    alter table post_tag add foreign key (tag_id) references tag (id);
    alter table post_tag add foreign key (post_id) references post (id);


    -- --password:123
    insert into user(fullname, email, password) values ('QuyetVV', 'quyeta2ubqn@gmail.com','$2a$10$GBCQjLA5BstflUfAlS/NUecsupn/5M6/Zshea3d9YJiRoAStcJ5EC');
    -- --password:123
    insert into user(fullname, email, password) values ('abc', 'abc@gmail.com','$2a$10$GBCQjLA5BstflUfAlS/NUecsupn/5M6/Zshea3d9YJiRoAStcJ5EC');

    insert into role (name) values ('ROLE_ADMIN');
    insert into role (name) values ('ROLE_USER');


    insert into users_roles(user_id, role_id) values (1,1);
    insert into users_roles(user_id, role_id) values (2,2);

    insert into tag(name) values('N1');
    insert into tag(name) values('N2');
    insert into tag(name) values('N3');
    insert into tag(name) values('N4');
    insert into tag(name) values('N5');
    insert into tag(name) values('IT_Japanese');
    insert into tag(name) values('Java_Basic');
    insert into tag(name) values('Spring_Boot');
    insert into tag(name) values('Vocabulary');
    insert into tag(name) values('Kanji ');
    insert into tag(name) values('Grammar');
    insert into tag(name) values('Reading');
    insert into tag(name) values('Exam');



