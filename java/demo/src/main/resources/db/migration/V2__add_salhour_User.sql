-- V2: User 테이블에 salhour 컬럼을 안전하게 추가
DROP PROCEDURE IF EXISTS AddColumnSalhour;
DELIMITER //
CREATE PROCEDURE AddColumnSalhour()
BEGIN
    IF NOT EXISTS (
        SELECT * FROM INFORMATION_SCHEMA.COLUMNS
        WHERE TABLE_SCHEMA = DATABASE()
        AND TABLE_NAME = 'User'
        AND COLUMN_NAME = 'salhour'
    ) THEN
        ALTER TABLE User ADD COLUMN salhour decimal(8, 2) default 0.0 not null;
    END IF;
END //
DELIMITER ;

CALL AddColumnSalhour();
DROP PROCEDURE AddColumnSalhour;
