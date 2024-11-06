package darkoverload.itzip.feature.algorithm.repository.tag.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatchInsertTagsImpl implements BatchInsertTags {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void batchInsertTags(List<Object[]> tagsToSave){
        String insertTagSql = "INSERT INTO problem_tags (boj_tag_id, display_name, display_name_sort, problem_count) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (boj_tag_id) DO NOTHING";
        jdbcTemplate.batchUpdate(insertTagSql, tagsToSave);
    }
}
