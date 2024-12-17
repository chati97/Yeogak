use yeogak;
-- token 컬럼 추가
-- alter table `User` add token varchar(1000) NULL;

-- 테스트용 트렁케이트
-- TRUNCATE TABLE `user`;
-- TRUNCATE TABLE attraction_review;


-- 랜덤 데이터 생성기
DELIMITER $$
DROP PROCEDURE IF EXISTS loopInsert$$
 
CREATE PROCEDURE loopInsert()
BEGIN
    DECLARE i INT DEFAULT 1;
        
    WHILE i <= 500 DO
        INSERT INTO `user`(user_id, user_pw, `name`, `nickname`, `email_id`, email_domain, birth_year, sex, `type`, sido, salt)
          VALUES(concat('dummyuser',i), concat('dummyuser',i), concat('최더미',i), concat('dummyuser',i), concat('dummyuser',i), 'ssafy.com', 1950 + i % 71, case when i % 2 = 0 then '남성' else '여성' end, 0, 1, 'salt');
        SET i = i + 1;
    END WHILE;
END$$
DELIMITER ;


CALL loopInsert;

select * from user;

