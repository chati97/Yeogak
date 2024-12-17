-- drop database if exists Yeogak;
-- create database if not exists Yeogak;
use Yeogak;

drop table if exists Attraction_Review;
drop table if exists Likes;
drop table if exists Tour_Plan_Attraction;
drop table if exists Tour_Plan_Review;
drop table if exists Tour_Plan;
drop table if exists QnA;
drop table if exists Banned;
drop table if exists Notice;
drop table if exists Comment;
drop table if exists Board;
drop table if exists User;

create table if not exists User (
	`user_id` varchar(20) primary key,
    `user_pw` varchar(200) not null,
    `name` varchar(20) not null,
    `nickname` varchar(20) not null unique,
    `email_id` varchar(20) not null,
    `email_domain` varchar(20) not null,
    `register_time` timestamp default now(),
    `birth_year` int not null,
    `sex` varchar(6) not null, 
    `type` int not null default 0,
    `sido` int,
    `salt` varchar(200) not null,
    `token` varchar(1000) null,
    foreign key(`sido`)
    references sido(`sido_code`) on delete cascade
);


create table if not exists Board (
	`id` int primary key auto_increment,
    `user_id` varchar(20) not null,
    `subject` varchar(100) not null,
    `content` varchar(5000) not null,
    `hit` int default 0,
    `likes` int default 0,
    `register_time` timestamp default now(),
    foreign key(`user_id`) references User(`user_id`) on delete cascade
);


create table if not exists Comment (
	`id` int primary key auto_increment,
    `board_id` int not null,
    `user_id` varchar(20) not null,
    `content` varchar(500) not null,
    `register_time` timestamp default now(),
    foreign key(`board_id`) references Board(`id`) on delete cascade,
    foreign key(`user_id`) references User(`user_id`) on delete cascade
);


create table if not exists Notice(
	`id` int primary key auto_increment,
    `user_id` varchar(20) not null,
    `subject` varchar(100) not null,
    `content` varchar(5000) not null,
    `hit` int default 0,
    `register_time` timestamp default now(),
    `need_to_notice` int not null default 1,
    foreign key(`user_id`) references User(`user_id`) on delete cascade
);

create table if not exists Banned (
	`id` int primary key auto_increment,
    `word` varchar(20) not null
);

create table if not exists QnA (
	`id` int primary key auto_increment,
    `user_id` varchar(20) not null,
    `subject` varchar(100) not null,
    `content` varchar(5000) not null,
    `register_time` timestamp default now(),
    `answer` varchar(5000),
    `answer_time` timestamp
);

create table if not exists Tour_Plan (
	`id` int primary key auto_increment,
    `user_id` varchar(20),
    `title` varchar(40) not null,
    `content` varchar(5000) not null,
    `start_time` timestamp not null,
    `end_time` timestamp not null,
    `register_time` timestamp default now(),
    `hit` int default 0,
    foreign key(`user_id`) references `User`(`user_id`) on delete cascade
);

create table if not exists Tour_Plan_Review (
	`id` int primary key,
    `content` varchar(5000) not null,
    `register_time` timestamp default now(),
    foreign key(`id`) references `Tour_Plan`(`id`) on delete cascade
);

-- 여행계획에 포함된 여행지들 아이디 저장하는 테이블 하나
create table if not exists Tour_Plan_Attraction (
    `idx` int auto_increment,
	`plan_id` int,
    `content_id` int,
    primary key (`idx`,`plan_id`, `content_id`),
    foreign key(`plan_id`) references `Tour_Plan`(`id`) on delete cascade,
    foreign key(`content_id`) references `attraction_info`(`content_id`) on delete cascade
);


create table if not exists Attraction_Review (
	`id` int primary key auto_increment,
    `user_id` varchar(20) not null,
    `attraction_id` int not null,
    `title` varchar(40),
    `content` varchar(2000),
    `rating` int,
    `register_time` timestamp default now(),
    `img_id` int,
    foreign key(`user_id`) references `User`(`user_id`),
    foreign key(`attraction_id`) references attraction_info(`content_id`) on delete cascade
);

create table if not exists Likes (
	`board_id` int,
    `user_id` varchar(20),
    primary key(`board_id`, `user_id`),
    foreign key(`board_id`) references `Board`(`id`) on delete cascade,
    foreign key(`user_id`) references `User`(`user_id`) on delete cascade
);
