
CREATE TABLE blogs
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT    NOT NULL,
    intro       TEXT,
    is_public   BOOLEAN   NOT NULL,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO blogs (user_id, intro, is_public, create_date, modify_date)
VALUES (1, 'This is a sample blog introduction.', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
