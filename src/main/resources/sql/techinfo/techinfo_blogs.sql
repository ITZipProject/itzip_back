-- 테이블 생성: blogs
CREATE TABLE blogs (
                       user_id BIGINT PRIMARY KEY,                -- 블로그 소유자의 회원 ID (Primary Key)
                       intro VARCHAR(255),                        -- 블로그 소개글 (최대 255자)
                       is_public BOOLEAN NOT NULL,                -- 블로그의 공개 여부 (true: 공개, false: 비공개)
                       create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 생성일 (자동 생성)
                       modify_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  -- 수정일 (수동 업데이트 필요)
);