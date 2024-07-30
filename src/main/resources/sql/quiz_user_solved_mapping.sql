CREATE TABLE quiz_user_solved_mapping (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    _id VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    given_points INT NOT NULL,
    is_correct BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);