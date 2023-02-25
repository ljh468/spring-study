drop table member if exists cascade;
create table member (
                        member_id varchar(10),
                        money integer not null default 0,
                        primary key (member_id)
);

set autocommit true; -- 자동 커밋 모드 설정
set autocommit false; -- 수동 커밋 모드 설정
commit; -- 수동 커밋

insert into member(member_id, money) values ('hi1',10000);
insert into member(member_id, money) values ('hi2',20000);

-- 데이터 초기화
set autocommit false;
delete from member;
insert into member(member_id, money) values ('oldId',10000);
commit;

-- lock 대기시간 설정
SET LOCK_TIMEOUT 60000;