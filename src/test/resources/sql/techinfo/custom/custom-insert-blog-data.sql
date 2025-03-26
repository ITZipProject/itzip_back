
INSERT INTO users(id, email, nickname, password, image_url, authority, sns_type, create_date, modify_date)
VALUES (999, 'dev.hyoseung@gmail.com', 'hyoseung', 'password', '', 'USER', '', '2025-03-04 00:00:00', '2025-03-04 00:00:00');

INSERT INTO users(id, email, nickname, password, image_url, authority, sns_type, create_date, modify_date)
VALUES (1000, '20181189@vision.hoseo.edu', 'rowing', 'password', '', 'USER', '', '2025-03-04 00:00:00', '2025-03-04 00:00:00');

INSERT INTO blogs(user_id, intro, created_at, updated_at)
VALUES (999, '당신만의 블로그 소개글을 작성해주세요.', '2025-03-04 00:00:00', '2025-03-04 00:00:00');
