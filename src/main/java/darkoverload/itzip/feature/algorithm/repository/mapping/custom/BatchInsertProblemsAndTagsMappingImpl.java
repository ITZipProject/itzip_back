package darkoverload.itzip.feature.algorithm.repository.mapping.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatchInsertProblemsAndTagsMappingImpl implements BatchInsertProblemsAndTagsMapping {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void batchInsertProblemsAndTagsMapping(List<Object[]> mappingsToSave){
        String insertMappingSql = "INSERT INTO problem_tag_mapping (problem_id, boj_tag_id) " +
                "VALUES (?, ?) " +
                "ON CONFLICT (problem_id, boj_tag_id) DO NOTHING";

        jdbcTemplate.batchUpdate(insertMappingSql, mappingsToSave);
    }
}
