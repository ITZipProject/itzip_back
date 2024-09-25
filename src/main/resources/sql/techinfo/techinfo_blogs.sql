-- 테이블 생성: blogs
CREATE TABLE blogs (
                       id BIGSERIAL PRIMARY KEY, -- 블로그 ID (Primary Key)
                       intro TEXT,               -- 블로그 소개글
                       user_id BIGINT NOT NULL,  -- 사용자 ID (Foreign Key로 사용될 수 있음)
                       create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 생성일 (자동으로 생성됨)
                       modify_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  -- 수정일 (자동으로 업데이트됨)
);