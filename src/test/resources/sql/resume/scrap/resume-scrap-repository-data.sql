insert into resumes (id, email, image_url, introduction, links, phone, public_on_off, subject, user_id, create_date, modify_date)
values (1L, 'itzip@gmail.com', 'https://itzip.com', '잇집입니다.', '잇집이력서', '010-2355-9839', 'YES', '잇집 홍길동',
        1L, '2024-07-22 10:00:05', '2024-07-22 10:00:05');

insert into users (id, authority, email, nickname, password, image_url, create_date, modify_date)
values (1L, 'USER', 'itzip@gmail.com', 'doudoudou', 'asdfg1234', 'https://itzip.com', '2024-07-22 10:00:05',
        '2024-07-22 10:00:05');

insert into resumes (id, email, image_url, introduction, links, phone, public_on_off, subject, user_id,
                     create_date, modify_date)
values (2L, 'itzip@gmail.com', 'https://itzip.com', '한길동입니다.', '한길동이력서', '010-2355-9839', 'YES', '잇집 한길동',
        1L, '2024-07-22 10:00:05', '2024-07-22 10:00:05');

insert into resume_scraps(id, resume_id, user_id, create_date, modify_date)
values (1L, 1L, 1L, '2024-07-22 10:00:05', '2024-07-22 10:00:05');

insert into resume_scraps(id, resume_id, user_id, create_date, modify_date)
values (2L, 2L, 1L, '2024-07-22 10:00:05', '2024-07-22 10:00:05');