package darkoverload.itzip.feature.job.repository.connect;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JobInfoConnectJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public int deleteAll(List<Long> positionIds) {
        String sql = "DELETE FROM job_infos WHERE position_id = ?";

        int[][] batchUpdate = jdbcTemplate.batchUpdate(sql, positionIds, 500, (ps, positionId) -> {
            ps.setLong(1, positionId);
        });

        return Arrays.stream(batchUpdate).flatMapToInt(Arrays::stream).sum();
    }

    public int saveAll(List<JobInfo> jobInfos) {
        String sql = "INSERT INTO job_infos(position_id, company_name, url, title, industry_name, location_code, location_name, job_name, experience_min, experience_max, experience_name, keyword, posting_date, expiration_date, scrap_count, create_date, modify_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int[][] batchInsert = jdbcTemplate.batchUpdate(sql, jobInfos, 500, (ps, jobInfo) -> {
            ps.setLong(1, jobInfo.getPositionId());
            ps.setString(2, jobInfo.getCompanyName());
            ps.setString(3, jobInfo.getUrl());
            ps.setString(4, jobInfo.getTitle());
            ps.setString(5, jobInfo.getIndustryName());
            ps.setString(6, jobInfo.getLocationCode());
            ps.setString(7, jobInfo.getLocationName());
            ps.setString(8, jobInfo.getJobName());
            ps.setLong(9, jobInfo.getExperienceMin());
            ps.setLong(10, jobInfo.getExperienceMax());
            ps.setString(11, jobInfo.getExperienceName());
            ps.setString(12, jobInfo.getKeyword());
            ps.setObject(13, jobInfo.getPostingDate());
            ps.setObject(14, jobInfo.getExpirationDate());
            ps.setLong(15, jobInfo.getScrapCount());
            ps.setObject(16, LocalDateTime.now());
            ps.setObject(17, LocalDateTime.now());
        });

        return Arrays.stream(batchInsert).flatMapToInt(Arrays::stream).sum();
    }

    public int updateAll(List<JobInfo> jobInfos) {
        String sql = "UPDATE job_infos SET " +
                "company_name = ?, " +
                "url = ?, " +
                "title = ?, " +
                "industry_name = ?, " +
                "location_code = ?, " +
                "location_name = ?, " +
                "job_name = ?, " +
                "experience_min = ?, " +
                "experience_max = ?, " +
                "experience_name = ?, " +
                "keyword = ?, " +
                "posting_date = ?, " +
                "expiration_date = ?, " +
                "modify_date = ? " +
                "where position_id = ?";

        int[][] batchUpdate = jdbcTemplate.batchUpdate(sql, jobInfos, 500, (ps, jobInfo) -> {
            ps.setString(1, jobInfo.getCompanyName());
            ps.setString(2, jobInfo.getUrl());
            ps.setString(3, jobInfo.getTitle());
            ps.setString(4, jobInfo.getIndustryName());
            ps.setString(5, jobInfo.getLocationCode());
            ps.setString(6, jobInfo.getLocationName());
            ps.setString(7, jobInfo.getJobName());
            ps.setLong(8, jobInfo.getExperienceMin());
            ps.setLong(9, jobInfo.getExperienceMax());
            ps.setString(10, jobInfo.getExperienceName());
            ps.setString(11, jobInfo.getKeyword());
            ps.setObject(12, jobInfo.getPostingDate());
            ps.setObject(13, jobInfo.getExpirationDate());
            ps.setObject(14, LocalDateTime.now());
            ps.setLong(15, jobInfo.getPositionId());
        });

        return Arrays.stream(batchUpdate).flatMapToInt(Arrays::stream).sum();
    }

}
