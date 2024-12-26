drop schema if exists sport_tracker;
create schema if not exists sport_tracker;
use sport_tracker;

create table if not exists league (
    id int auto_increment primary key,
    name varchar(255) not null
);

create table if not exists season (
    id int auto_increment primary key,
    year int not null,
    leagueId int not null
);

create table if not exists team (
    id int auto_increment primary key,
    name varchar(255) not null,
    leagueId int not null,
    logoUrl varchar(255) not null
);

create table if not exists game (
    id int auto_increment primary key,
    date date not null,
    seasonId int not null,
    homeTeamId int not null,
    awayTeamId int not null,
    homeTeamScore int not null,
    awayTeamScore int not null,
    homeTeamScoreQ1 int,
    homeTeamScoreQ2 int,
    homeTeamScoreQ3 int,
    homeTeamScoreQ4 int,
    awayTeamScoreQ1 int,
    awayTeamScoreQ2 int,
    awayTeamScoreQ3 int,
    awayTeamScoreQ4 int,
    homePassing text,
    awayPassing text,
    homeRushing text,
    awayRushing text,
    homeReceiving text,
    awayReceiving text
);

create table if not exists player (
    id int auto_increment primary key,
    name varchar(255) not null,
    teamId int,
    position varchar(3) not null,
    number int
);

create table if not exists passingStats (
    id int auto_increment primary key,
    gameId int not null,
    homeOrAway varchar(4) not null,

    playerId int not null,
    playerNumber int not null,
    playerName varchar(255) not null,

    attempts int not null,
    completions int not null,
    yards int not null,
    touchdowns int not null,
    interceptions int not null
);

create table if not exists rushingStats (
    id int auto_increment primary key,
    gameId int not null,
    homeOrAway varchar(4) not null,

    playerId int not null,
    playerNumber int not null,
    playerName varchar(255) not null,

    attempts int not null,
    yards int not null,
    touchdowns int not null,
    longest int not null
);

create table if not exists receivingStats(
    id int auto_increment primary key,
    gameId int not null,
    homeOrAway varchar(4) not null,

    playerId int not null,
    playerNumber int not null,
    playerName varchar(255) not null,

    receptions int not null,
    yards int not null,
    touchdowns int not null,
    longest int not null
);
