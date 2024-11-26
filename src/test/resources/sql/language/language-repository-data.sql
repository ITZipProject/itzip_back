insert into users (id, authority, email, nickname, password, image_url, create_date, modify_date)
values (1L, 'USER', 'itzip@gmail.com', 'doudoudou', 'asdfg1234', 'https://itzip.com', '2024-07-22 10:00:05',
        '2024-07-22 10:00:05');

insert into resumes (id, email, image_url, introduction, links, phone, public_on_off, subject, user_id,
                     create_date, modify_date)
values (1L, 'itzip@gmail.com', 'https://itzip.com', '잇집입니다.', '잇집이력서', '010-2355-9839', 'YES', '잇집 홍길동',
        1L, '2024-09-26 10:00:05', '2024-09-26 10:00:05');

insert into languages (id, create_date, modify_date, acquisition_date, name, score, resume_id) values(1L, '2024-09-26 10:00:05', '2024-09-26 10:00:05', '2022-11-20 10:00:05', 'TOEIC', 900, 1L);

insert into languages (id, create_date, modify_date, acquisition_date, name, score, resume_id) values(2L, '2024-11-26 10:00:05', '2024-11-26 10:00:05', '2022-11-29 10:00:05', 'TOEFL', 860, 1L);