-- 테이블 생성: blogs
CREATE TABLE blogs
(
    user_id     BIGINT PRIMARY KEY,                                                       -- 블로그의 고유 식별자, 회원 ID와 동일하게 사용됨
    intro       VARCHAR(255),                                                             -- 블로그 소개글 (최대 255자)
    is_public   BOOLEAN   NOT NULL,                                                       -- 블로그 공개 여부 (true: 공개, false: 비공개)
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,                             -- 생성일 (자동 생성)
    modify_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정일 (자동 업데이트)
    CONSTRAINT fk_blog_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE -- 외래 키 제약 조건, 회원 ID를 참조함
);