
INSERT INTO users(id, email, nickname, password, image_url, authority, sns_type, create_date, modify_date)
VALUES (999, 'dev.hyoseung@gmail.com', 'hyoseung', 'password', '', 'USER', '', '2025-03-04 00:00:00', '2025-03-04 00:00:00');

INSERT INTO comments(id, user_id, article_id, content, created_at, updated_at, is_displayed)
VALUES (999, 999, '67d2b940d88d2b9236a1fb0e', 'content', '2025-03-04 00:00:00', '2025-03-04 00:00:00', TRUE);
