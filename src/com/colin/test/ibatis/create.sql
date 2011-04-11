create table jc_clob(
  id number,
  name varchar2(30),
  text clob
);

insert into jc_clob values(1,'jc1','11111111111');
insert into jc_clob values(2,'jc2','22222222222');
insert into jc_clob values(3,'jc3','33333333333');

create table jc_test(
  id number,
  name varchar2(30)
);
insert into jc_test values(1,'jc1');
insert into jc_test values(2,'jc2');
insert into jc_test values(3,'jc3');

create sequence jc_seq start with 100;

create table jc_nclob(
  id number,
  name varchar2(30),
  text nclob
);

insert into jc_nclob values(1,'jc1','11111111111');
