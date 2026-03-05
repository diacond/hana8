SET FOREIGN_KEY_CHECKS = 0;
truncate table User;
truncate table Member;
truncate table Post;
SET FOREIGN_KEY_CHECKS = 1;

SET time_zone = 'Asia/Seoul';

insert into User(username, email, telno, bloodType)
values ('Hong', 'hong@gmail.com', '01012345678', 'A'),
       ('kim', 'kim@gmail.com', '01012345679', 'B');

insert into Member (nickname, email, password, isActive, bloodType)
values ('hong', 'hong@gmail.com', 1234, 1, 'A'),
       ('kim', 'kim@gmail.com', 1234, 1, 'B'),
       ('lee', 'lee@gmail.com', 1234, 1, 'AB');
