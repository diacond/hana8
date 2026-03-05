-- V3: bloodType 추가, Member 테이블 생성, 초기 Post 테이블 생성

-- 1. bloodType 컬럼 추가
DROP PROCEDURE IF EXISTS AddColumnBloodType;
DELIMITER //
CREATE PROCEDURE AddColumnBloodType()
BEGIN
    IF NOT EXISTS (
        SELECT * FROM INFORMATION_SCHEMA.COLUMNS
        WHERE TABLE_SCHEMA = DATABASE()
        AND TABLE_NAME = 'User'
        AND COLUMN_NAME = 'bloodType'
    ) THEN
        ALTER TABLE User ADD COLUMN bloodType enum ('A','AB','B','O');
    END IF;
END //
DELIMITER ;
CALL AddColumnBloodType();
DROP PROCEDURE AddColumnBloodType;

-- 2. Member 테이블 생성
CREATE TABLE IF NOT EXISTS Member
(
    id        int unsigned                                                    not null auto_increment,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             not null,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
    nickname  varchar(30)                                                     not null,
    email     varchar(255)                                                    not null,
    passwd    varchar(255),
    bloodType enum ('A','AB','B','O'),
    isActive  bit       default false                                         not null,
    primary key (id)
);

-- Member 테이블 제약조건 추가
DROP PROCEDURE IF EXISTS AddUniqueConstraintMemberEmail;
DELIMITER //
CREATE PROCEDURE AddUniqueConstraintMemberEmail()
BEGIN
    IF NOT EXISTS (
        SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
        WHERE TABLE_SCHEMA = DATABASE()
        AND TABLE_NAME = 'Member'
        AND CONSTRAINT_NAME = 'uniq_Member_email'
    ) THEN
        ALTER TABLE Member ADD CONSTRAINT uniq_Member_email UNIQUE (email);
    END IF;
END //
DELIMITER ;
CALL AddUniqueConstraintMemberEmail();
DROP PROCEDURE AddUniqueConstraintMemberEmail;

-- 3. 초기 Post 테이블 생성
CREATE TABLE IF NOT EXISTS Post
(
    id        varchar(20)                         not null primary key,
    createdAt timestamp default CURRENT_TIMESTAMP not null,
    updatedAt timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    title     varchar(255)                        null
);
