drop TABLE quiz_user_solved_mapping;

CREATE TABLE quiz_user_solved_mapping (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    _id VARCHAR(50) NOT NULL,
    time_stamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    given_points INT NOT NULL,
    user_quiz_status varchar(10),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);