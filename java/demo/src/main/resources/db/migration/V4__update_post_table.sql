-- V4: Post 테이블을 최종 명세로 재생성 (ID BIGINT AUTO_INCREMENT, body, writer 추가)

DROP TABLE IF EXISTS Post;

CREATE TABLE Post
(
    id        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    title     VARCHAR(255)                                                    NULL,
    body      VARCHAR(2000)                                                   NULL,
    writer    VARCHAR(31)                                                     NOT NULL
);
