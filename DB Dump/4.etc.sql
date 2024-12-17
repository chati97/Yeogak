use yeogak;
 
desc User;
select * from user;
-- user.register
insert into User (`user_id`, `user_pw`, `name`, `nickname`, `email_id`, `email_domain`, `birth_year`, `sex`, `sido`, `salt`) 
values
('ssafy1', 'ssafy', '김싸피', '여행날', 'ssafy', 'ssafy', 1990, '남성', 1, "1"),
('ssafy2', 'ssafy', '박싸피', '강태공', 'ssafy', 'ssafy', 2000, '여성', 2, "2"),
('ssafy3', 'ssafy', '이싸피', '축구', 'ssafy', 'ssafy', 1980, '남성', 31, "3"),
('ssafy4', 'ssafy', '최싸피', '솔로', 'ssafy', 'ssafy', 2010, '여성', 39, "4"),
('ssafy5', 'ssafy', '강싸피', '길따라', 'ssafy', 'ssafy', 1980, '남성', 8, "5")
;

insert into User (`user_id`, `user_pw`, `name`, `nickname`, `email_id`, `email_domain`, `birth_year`, `sex`, `sido`, `salt`) 
values ('admin1', 'admin', '관리자', '관리자', 'admin', 'admin', 1990, '남성', 1, "1");
update user set type=1 where user_id='admin1';
desc Board;
-- board.write
insert into Board (`user_id`, `subject`, `content`)
values
('ssafy1', '오늘의 운세', '오늘의 운세는 세옹지마입니다. 나쁜 일 뒤에는 좋은 일이 따르기 마련이니 긍정적인 마음으로 생활합시다'),
('ssafy3', '여행지 추천받아요', '혼자 여행을 떠나보려고 합니다. 국내에 좋은 여행지가 있다면 추천해주세요!'),
('ssafy5', '오늘은 날씨가 맑네요', '회사를 벗어나 넓은 들판이 있는 곳으로 여행을 가고싶은 날이네요. 휴가가 빨리 왔으면 좋겠습니다')
;

desc Comment;
-- comment.write
insert into Comment(`board_id`, `user_id`, `content`)
values
('1', 'ssafy2', '오늘도 올라온 운세! 항상 올려주셔서 감사합니다!'),
('1', 'ssafy3', '오늘 아침에 지갑을 두고와서 점심을 먹지 못하고 있었는데 친구가 밥을 사줬네요. 이것도 세옹지마일까요?')
;

desc Notice;
-- notice.write
insert into Notice(`user_id`, `subject`, `content`)
values ('admin1', '서버 점검 안내입니다', '2023년 5월 30일 13시부터 14시까지 서버 점검이 있을 예정입니다. 이용에 참고 바랍니다');

desc QnA;
insert into QnA (`user_id`, `subject`, `content`)
values
('ssafy1', '이메일이 전송되지 않습니다', '이메일 변경을 위해 인증 요청을 보냈지만 메일이 오지 않아서 문의드립니다')
;