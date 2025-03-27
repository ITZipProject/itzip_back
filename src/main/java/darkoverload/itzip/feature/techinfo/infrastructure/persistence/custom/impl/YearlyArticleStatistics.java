package darkoverload.itzip.feature.techinfo.infrastructure.persistence.custom.impl;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "연도별 아티클 통계")
public record YearlyArticleStatistics(
        @Schema(description = "연도", example = "2025")
        int year,
        @Schema(description = "해당 연도의 월별 통계", required = true)
        List<MonthlyArticleStatistics> months
) { }

@Schema(description = "월별 아티클 통계")
record MonthlyArticleStatistics(
        @Schema(description = "월", example = "3")
        int month,
        @Schema(description = "해당 월의 주별 통계", required = true)
        List<WeeklyArticleStatistics> weeks
) { }

@Schema(description = "주별 아티클 통계")
record WeeklyArticleStatistics(
        @Schema(description = "주", example = "2")
        int week,
        @Schema(description = "해당 주의 아티클 수", example = "5")
        long articleCount
) { }
