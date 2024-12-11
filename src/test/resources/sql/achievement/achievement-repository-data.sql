insert into users (id, authority, email, nickname, password, image_url, create_date, modify_date)
values (1L, 'USER', 'itzip@gmail.com', 'doudoudou', 'asdfg1234', 'https://itzip.com', '2024-07-22 10:00:05',
        '2024-07-22 10:00:05');

insert into resumes (id, email, image_url, introduction, links, phone, public_on_off, subject, user_id, create_date,
                     modify_date, file_urls)
values (1L, 'itzip@gmail.com', 'https://itzip.com', '잇집입니다.', '잇집이력서', '010-2355-9839', 'YES', '잇집 홍길동', 1L,
        '2024-09-26 10:00:05', '2024-09-26 10:00:05', null);

insert into achievements (id, create_date, modify_date, achievement_date, content, name, organization, resume_id)
values (1L, '2024-11-26 23:59:59', '2024-11-26 23:59:59', '2024-08-20 10:10:10', '자바 잘해서줍니다.', '잇집자바상', '잇집', 1L);

insert into achievements (id, create_date, modify_date, achievement_date, content, name, organization, resume_id)
values (2L, '2024-11-26 23:59:59', '2024-11-26 23:59:59', '2024-06-20 09:10:10', '코틀린 잘해서줌', '잇집코틀린상', '잇집', 1L);