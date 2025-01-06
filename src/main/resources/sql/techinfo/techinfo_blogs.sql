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

INSERT INTO users (id, create_date, modify_date, authority, email, nickname, password, image_url)
VALUES (1,
        '2024-08-26 01:00:56.653362',
        '2024-08-26 01:00:56.653362',
        'USER',
        'dev.hyoseung@gmail.com',
        'hyoseung',
        '$2a$10$JV5r580j9yGtPwj8FsPpo.UQ.H6j25fLZG6H1iYbtX4rYOCsQUXVa',
        'https://dy1vg9emkijkn.cloudfront.net/profile/19cc111f-c8f4-4d64-bd7a-129415e3ffa2.jpg');

INSERT INTO blogs (user_id, intro, is_public, create_date, modify_date)
VALUES (1, 'This is a sample blog introduction.', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
