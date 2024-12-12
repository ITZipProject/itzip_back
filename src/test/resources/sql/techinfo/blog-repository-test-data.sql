
insert into users(id, authority, email, nickname, password, create_date, modify_date)
values (100, 'USER', 'test@test.com', '아름다운 135번째 돌고래', '$2a$10$5RifHVaUMq.7IXyJK40kpuaWzfhRBsPgdq1CAhB6LGXdwbxep0.Ba',
        '2024-07-22 10:13:19.129274', '2024-07-22 10:13:19.129274');

INSERT INTO blogs(id, user_id, intro, is_public, create_date, modify_date)
values (100, 100, '블로그 소개 예시입니다.', true, '2024-07-23 10:13:19.129274', '2024-07-23 10:13:19.129274');
