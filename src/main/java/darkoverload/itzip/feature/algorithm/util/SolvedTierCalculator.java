package darkoverload.itzip.feature.algorithm.util;

import darkoverload.itzip.feature.algorithm.domain.Problem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SolvedTierCalculator {
    //(문제 난이도 합 * 2) + (200 * [1 - 0.99**사용자가 푼 문제수]) 문제들이 주어지면 레이팅 계산하는 메서드(tag전용)
    public int tagRatingCalculator(List<Problem> solvedProblems){
        return ((solvedProblems.stream().mapToInt(Problem::getLevel).sum() * 2) +
                (int) (200 * (1 - Math.pow(0.99, solvedProblems.size()))));
    }

    //사용자의 레이팅을 기반으로 티어를 계산해주는 메서드
    public int tierCalculator(int rating){
        for (int tier = tiers.length - 1; tier >= 0; tier--){
            if (rating >= tiers[tier]){
                return tier;
            }
        }
        return 0;
    }

    //티어 레이팅 매핑 정보
    private final int[] tiers = {
            0,     // Unrated
            30,    // Bronze V
            60,    // Bronze IV
            90,    // Bronze III
            120,   // Bronze II
            150,   // Bronze I
            200,   // Silver V
            300,   // Silver IV
            400,   // Silver III
            500,   // Silver II
            650,   // Silver I
            800,   // Gold V
            950,   // Gold IV
            1100,  // Gold III
            1250,  // Gold II
            1400,  // Gold I
            1600,  // Platinum V
            1750,  // Platinum IV
            1900,  // Platinum III
            2000,  // Platinum II
            2100,  // Platinum I
            2200,  // Diamond V
            2300,  // Diamond IV
            2400,  // Diamond III
            2500,  // Diamond II
            2600,  // Diamond I
            2700,  // Ruby V
            2800,  // Ruby IV
            2850,  // Ruby III
            2900,  // Ruby II
            2950,  // Ruby I
            3000   // Master
    };
}