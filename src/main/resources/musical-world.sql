create database musical_world character set utf8 collate utf8_general_ci;

use musical_world;

create table user(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  surname varchar(255) not null ,
  username varchar(255) not null unique ,
  password varchar(255) not null ,
  role varchar(255) not null ,
  img_url varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table news(
  id int not null auto_increment primary key ,
  title varchar(255) not null ,
  descritpion text not null ,
  img_url varchar(255) not null ,
  created_date datetime not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table comment(
  id int not null auto_increment primary key ,
  comment text not null ,
  news_id int ,
  user_id int not null ,
  send_date datetime null ,
  parent_id int ,
  foreign key (news_id) references news(id) on delete cascade ,
  foreign key (user_id) references user(id) on delete cascade ,
  foreign key (parent_id) references comment(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table category(
  id int not null auto_increment primary key ,
  name_en varchar(255) not null ,
  name_ru varchar(255) not null ,
  name_arm varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table music(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  mp3 varchar(255) not null,
  year int(4) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table category_music(
  category_id int not null ,
  music_id int not null ,
  foreign key (category_id) references category(id) on delete cascade ,
  foreign key (music_id) references music(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table bookmark(
  user_id int not null ,
  music_id int not null ,
  foreign key (user_id) references user(id) on delete cascade ,
  foreign key (music_id) references music(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table musician(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  surname varchar(255) not null ,
  birth_date date not null ,
  biography text not null ,
  img_url varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table album(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  description text not null ,
  release_date date not null ,
  img_url varchar(255) not null ,
  musician_id int not null ,
  foreign key (musician_id) references musician(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table album_music(
  album_id int not null ,
  music_id int not null ,
  foreign key (album_id) references album(id) on delete cascade ,
  foreign key (music_id) references music(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table home(
  id int not null auto_increment primary key ,
  home_img varchar(255) not null ,
  home_mp3_id int not null ,
  musician_id int not null ,
  foreign key (home_mp3_id) references music(id),
  foreign key (musician_id) references musician(id)
)engine InnoDB character set utf8 collate utf8_general_ci;


insert into category(name_en, name_ru, name_arm) values
  ('Rock','Рок','Ռոք'),
  ('Rap','Рэп','Ռեպ'),
  ('Hip-hop','Хип-хоп','Հիպ Հոփ'),
  ('Metal','Металл','Մետալ'),
  ('Shanson','Шансон','Շանսոն'),
  ('Alternative','Альтернатива','Ալտերնատիվ'),
  ('Dance Discotheque','Танцевальная Дискотечная','Պարային Ակումբային'),
  ('Dubstep','Дабстеп','Դուբստեպ'),
  ('Pop','Поп','Փոփ');

insert into user(name, surname, username, password, role, img_url) VALUES
  ('Admin','Admin','admin','$2a$04$J3JfeJDUEG3iOundNgood.mbNnyh7IE0DJl4HT08Sba.0RtkcXeBu','ADMIN','admin/Beagle-On-White-01-400x267.jpg');
