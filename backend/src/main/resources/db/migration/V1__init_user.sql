drop table if exists f1user cascade;
drop sequence if exists user_seq;
create sequence user_seq start with 1 increment by 1;
create table f1user (id integer not null, name varchar(255), password varchar(255), username varchar(255), primary key (id));

insert into f1user (id, name, username, password) values ((select nextval('user_seq')), 'Admin', 'admin', '$2a$10$vlROi1Xn5hiZsjtIIC3HT.zHVNRKnxHa4w1Fp.Wt67X2OAFYbqdFm');