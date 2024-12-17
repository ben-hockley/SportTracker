drop schema if exists sport_tracker;
create schema if not exists sport_tracker;
use sport_tracker;

create table if not exists team (
    id int auto_increment primary key,
    name varchar(255) not null,
    league varchar(255) not null,
    logoUrl varchar(255) not null
);

create table if not exists game (
    id int auto_increment primary key,
    date date not null,
    homeTeamId int not null,
    awayTeamId int not null,
    homeTeamScore int not null,
    awayTeamScore int not null
)

create
