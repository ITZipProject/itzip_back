insert into users (id, authority, email, nickname, password, image_url, create_date, modify_date)
values (1L, 'USER', 'itzip@gmail.com', 'doudoudou', 'asdfg1234', 'https://itzip.com', '2024-07-22 10:00:05',
        '2024-07-22 10:00:05');

insert into resumes (id, email, image_url, introduction, links, phone, public_on_off, subject, user_id,
                     create_date, modify_date)
values (1L, 'itzip@gmail.com', 'https://itzip.com', '잇집입니다.', '잇집이력서', '010-2355-9839', 'YES', '잇집 홍길동',
        1L, '2024-09-26 10:00:05', '2024-09-26 10:00:05');

insert into educations(id, create_date, modify_date, end_date, major, school_name, start_date, resume_id) values(1L, '2024-11-26 23:59:59', '2024-11-26 23:59:59', '2020-03-11 23:59:59', '컴퓨터공학과', '잇집대학교','2024-03-26 23:59:59', 1L);

insert into educations(id, create_date, modify_date, end_date, major, school_name, start_date, resume_id) values(2L, '2024-11-26 23:59:59', '2024-11-26 23:59:59', '2024-03-26 23:59:59', '백엔드 과정', '잇집테킷','2024-09-28 23:59:59', 1L);
