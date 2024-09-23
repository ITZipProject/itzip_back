CREATE TABLE solvedac_users (
                                user_id BIGSERIAL REFERENCES users(id),
                                username VARCHAR(255),
                                rating INTEGER,
                                rank INTEGER,
                                update_time TIMESTAMP,
                                PRIMARY KEY (user_id)
);

CREATE TABLE problems (
                          problem_id BIGSERIAL PRIMARY KEY,
                          title VARCHAR(255),
                          level INTEGER,
                          accepted_user_count INTEGER,
                          averageTries INTEGER
);

CREATE TABLE problem_tags (
                              boj_tag_id BIGSERIAL PRIMARY KEY,
                              display_name VARCHAR(255),
                              display_name_sort VARCHAR(255)
);

CREATE TABLE user_problem_mapping (
                                      id BIGSERIAL PRIMARY KEY,
                                      user_id BIGINT REFERENCES solvedac_users(user_id) ON DELETE CASCADE,
                                      problem_id BIGINT REFERENCES problems(problem_id) ON DELETE CASCADE
);

CREATE TABLE problem_tag_mapping (
                                     id BIGSERIAL PRIMARY KEY,
                                     problem_id BIGINT REFERENCES problems(problem_id) ON DELETE CASCADE,
                                     boj_tag_id BIGINT REFERENCES problem_tags(boj_tag_id) ON DELETE CASCADE
);