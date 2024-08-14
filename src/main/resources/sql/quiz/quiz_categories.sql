drop table quiz_categories;

CREATE TABLE quiz_categories (
    id BIGSERIAL NOT NULL,
    category_name varchar(255) UNIQUE
);