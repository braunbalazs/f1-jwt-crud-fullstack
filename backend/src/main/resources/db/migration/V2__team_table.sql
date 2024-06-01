drop table if exists team cascade;
drop sequence if exists team_seq;
create sequence team_seq start with 1 increment by 1;

create table team
    (id integer not null, name varchar(255), year_established integer, championships_won integer, reg_fee_paid boolean, primary key (id));