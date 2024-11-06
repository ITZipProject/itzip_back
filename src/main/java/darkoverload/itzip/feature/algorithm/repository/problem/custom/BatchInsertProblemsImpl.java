package darkoverload.itzip.feature.algorithm.repository.problem.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatchInsertProblemsImpl implements BatchInsertProblems{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void batchInsertProblems(List<Object[]> problemsToSave){
        String insertProblemSql = "INSERT INTO problems (problem_id, title, level, accepted_user_count, average_tries) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (problem_id) DO UPDATE " +
                "SET title = EXCLUDED.title, level = EXCLUDED.level, accepted_user_count = EXCLUDED.accepted_user_count, average_tries = EXCLUDED.average_tries";

        jdbcTemplate.batchUpdate(insertProblemSql, problemsToSave);
    }
}