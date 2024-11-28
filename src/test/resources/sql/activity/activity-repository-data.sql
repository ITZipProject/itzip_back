insert into users (id, authority, email, nickname, password, image_url, create_date, modify_date)
values (1L, 'USER', 'itzip@gmail.com', 'doudoudou', 'asdfg1234', 'https://itzip.com', '2024-07-22 10:00:05',
        '2024-07-22 10:00:05');

insert into resumes (id, email, image_url, introduction, links, phone, public_on_off, subject, user_id,
                     create_date, modify_date)
values (1L, 'itzip@gmail.com', 'https://itzip.com', '잇집입니다.', '잇집이력서', '010-2355-9839', 'YES', '잇집 홍길동',
        1L, '2024-09-26 10:00:05', '2024-09-26 10:00:05');

insert into activities (id, create_date, modify_date, content, name, start_date, end_date, resume_id) values(1L, '2024-09-26 10:00:05', '2024-09-26 10:00:05', '무박 3일동안 해커톤 통해서 우수상 수상하였습니다.', '해커톤상', '2024-08-20 10:00:05', '2024-08-23 10:00:05', 1L);

insert into activities (id, create_date, modify_date, content, name, start_date, end_date, resume_id) values(2L, '2024-11-26 10:00:05', '2024-11-26 10:00:05', '동아리 활동', '코딩 동아리', '2022-11-20 10:00:05', '2023-11-20 10:00:05', 1L);