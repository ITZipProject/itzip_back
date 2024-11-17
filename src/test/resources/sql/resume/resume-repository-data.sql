insert into users (id, authority, email, nickname, password, image_url, create_date, modify_date)
values (1L, 'USER', 'itzip@gmail.com', 'doudoudou', 'asdfg1234', 'https://itzip.com', '2024-07-22 10:00:05',
        '2024-07-22 10:00:05');
insert into users (id, authority, email, nickname, password, image_url, create_date, modify_date)
values (2L, 'USER', 'ori@gmail.com', 'orioriori', 'asdfg1234', 'https://itzip.com', '2024-07-22 10:00:05',
        '2024-07-22 10:00:05');
insert into users (id, authority, email, nickname, password, image_url, create_date, modify_date)
values (3L, 'USER', 'qwedda@gmail.com', 'dasdsasaddsa', 'asdfg1234', 'https://itzip.com', '2024-07-22 10:00:05',
        '2024-07-22 10:00:05');

insert into resumes (id, email, image_url, introduction, links, phone, public_on_off, subject, user_id,
                     create_date, modify_date)
values (1L, 'itzip@gmail.com', 'https://itzip.com', '잇집입니다.', '잇집이력서', '010-2355-9839', 'YES', '잇집 홍길동',
        1L, '2024-07-22 10:00:05', '2024-07-22 10:00:05');
insert into resumes (id, email, image_url, introduction, links, phone, public_on_off, subject, user_id,
                     create_date, modify_date)
values (2L, 'park@gmail.com', 'https://itzip.com', 'park입니다.', 'park이력서', '010-2354-4444', 'YES',
        '잇집 박길동',
        2L, '2024-07-22 10:00:05', '2024-07-22 10:00:05');
insert into resumes (id, email, image_url, introduction, links, phone, public_on_off, subject, user_id,
                     create_date, modify_date)
values (3L, 'sin@gmail.com', 'https://itzip.com', 'sin입니다.', 'sin이력서', '010-2355-2331', 'YES', 'sin 홍길동',
        3L, '2024-07-22 10:00:05', '2024-07-22 10:00:05');
insert into resumes (id, email, image_url, introduction, links, phone, public_on_off, subject, user_id,
                     create_date, modify_date)
values (4L, 'lev@gmail.com', 'https://itzip.com', 'lev입니다.', 'lev이력서', '010-2354-4444', 'YES',
        'lev 잇집 박길동',
        2L, '2024-07-22 10:00:05', '2024-07-22 10:00:05');