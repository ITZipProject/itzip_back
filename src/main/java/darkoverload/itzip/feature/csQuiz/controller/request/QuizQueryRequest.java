package darkoverload.itzip.feature.csQuiz.controller.request;

import darkoverload.itzip.feature.csQuiz.entity.SortBy;
import lombok.Builder;
import lombok.Getter;

//퀴즈 쿼리에서 받아오는 파라메터값이 너무나도 많아서 이를 객체에 담아서 사용하려고함
@Getter
@Builder
public class QuizQueryRequest {
    //퀴즈 난이도 입력칸 1~3
    private Integer difficulty;
    //카테고리 식별값 입력칸
    private Long categoryId;
    //NEWEST, OLDEST
    private SortBy sortBy;
    //사용자가 푼문제를 포함하는지 ture면 포함 false면 미포함
    private boolean inUserSolved;
    //문제 페이지 0부터 시작
    private int page;
    //가져올 문제 수
    private int size;
    //검색할 단어
    private String keyword;
}
