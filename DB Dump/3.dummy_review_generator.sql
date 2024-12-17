use yeogak;

-- 랜덤 content_id에 리뷰 500개씩 생성
truncate table attraction_review;

DELIMITER $$
DROP PROCEDURE IF EXISTS review_generater$$
 
CREATE PROCEDURE review_generater()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE j INT DEFAULT 1;
	DECLARE random_content_id INT;
        
    WHILE i <= 15 DO
		SET random_content_id = (SELECT content_id FROM attraction_info ORDER BY RAND() LIMIT 1);
		
        SET j = 1;
        WHILE j <= 500 DO
			INSERT INTO attraction_review(user_id, attraction_id, rating)
			  VALUES(concat('dummyuser', j), random_content_id, RAND() * 5);
			SET j = j + 1;
		END WHILE;
        SET i = i + 1;
    END WHILE;
END$$
DELIMITER ;

CALL review_generater;

select * from attraction_review ;