package darkoverload.itzip.feature.csQuiz.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "정답인지 아닌지 알려주는 객체")
public enum UserQuizStatus {
    @Schema(description = "사용자가 맞췄을때 반환하는 객체")
    CORRECT,    // 사용자가 푼 문제 중 맞춘 문제
    @Schema(description = "사용자가 풀었지만 못맞춘 문제")
    INCORRECT,  // 사용자가 푼 문제 중 틀린 문제
    @Schema(description = "사용자가 풀지 않은 문제")
    UNSOLVED    // 사용자가 아직 풀지 않은 문제
}