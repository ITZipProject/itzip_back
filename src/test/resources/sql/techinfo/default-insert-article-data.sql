
INSERT INTO users(id, email, nickname, password, image_url, authority, sns_type, create_date, modify_date)
VALUES (999, 'dev.hyoseung@gmail.com', 'hyoseung', 'password', '', 'USER', '', '2025-03-04 00:00:00', '2025-03-04 00:00:00');

INSERT INTO blogs(user_id, intro, created_at, updated_at)
VALUES (999, '당신만의 블로그 소개글을 작성해주세요.', '2025-03-04 00:00:00', '2025-03-04 00:00:00');

INSERT INTO scraps(id, user_id, article_id, created_at)
VALUES (999, 999, '67d2b940d88d2b9236a1fb0e', '2025-03-04 00:00:00');

INSERT INTO likes(id, user_id, article_id, created_at)
VALUES (999, 999, '67d2b940d88d2b9236a1fb0e', '2025-03-04 00:00:00');
