package darkoverload.itzip.feature.techinfo.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(
        description = "포스트 생성 요청 객체로, 포스트에 필요한 데이터를 전달합니다."
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @Schema(description = "포스트가 속한 카테고리 ID", example = "66ce18d84cb7d0b29ce602f5")
    @NotBlank(message = "카테고리 ID는 필수 입력 항목입니다.")
    private String categoryId;

    @Schema(description = "포스트 제목", example = "밤하늘 아래, 감정의 여정")
    @NotBlank(message = "포스트 제목은 필수 입력 항목입니다.")
    private String title;

    @Schema(
            description = "포스트 본문 내용",
            example = """
                세 개의 이미지는 감정의 여정을 표현한다. 첫 번째 장면에서는 소녀가 자유를 꿈꾸며 하늘로 날아오르지만 현실의 무게에 의해 추락하는 모습을 담고 있다. 
                이는 인간이 꿈을 꾸고 좌절을 마주하는 과정을 상징한다. 두 번째 장면에서는 소녀가 밤하늘을 바라보며 과거의 기억과 꿈을 되새긴다. 창문 너머로 보이는 파도는 그녀의 내면의 감정을 표현한다. 
                마지막 장면은 비 내리는 거리에서 두 사람이 함께 걸어가는 모습으로, 서로에게 의지하는 관계를 보여준다. 빗소리와 고요한 풍경은 그들의 감정을 더욱 부각시킨다.
                이 이미지는 꿈, 좌절, 고독, 그리고 위안을 주제로 하여 인간이 감정을 마주하고 성장하는 과정을 담아냈다.
                """
    )
    @NotBlank(message = "포스트 본문은 필수 입력 항목입니다.")
    private String content;

    @Schema(
            description = "포스트의 썸네일 이미지 경로",
            example = "https://dy1vg9emkijkn.cloudfront.net/techinfo/19cc111f-c8f4-4d64-bd7a-129415e3ffa2.jpg"
    )
    @NotBlank(message = "썸네일 이미지 경로는 필수 입력 항목입니다.")
    private String thumbnailImagePath;

    @Schema(
            description = "포스트 본문 이미지 경로 리스트",
            example = """
                  [
                    "https://dy1vg9emkijkn.cloudfront.net/techinfo/7635bb80-416a-4042-a901-552df46351a8.png",
                    "https://dy1vg9emkijkn.cloudfront.net/techinfo/50d081ca-b2f5-4162-926f-0f061aec2554.png"
                  ]
                  """
    )
    private List<String> contentImagePaths;
}