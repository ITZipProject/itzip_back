package darkoverload.itzip.feature.techinfo.ui.payload.request.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "아티클 변경 요청에 대한 Payload")
public record ArticleEditRequest(
        @Schema(
                description = "아티클 ID",
                example = "66e724e50000000000db4e53"
        )
        @NotBlank(message = "아티클 ID는 필수입니다.")
        @JsonProperty("article_id") String articleId,

        @Schema(
                description = "아티클 유형",
                example = "other"
        )
        @NotBlank(message = "아티클 유형은 필수입니다.")
        String type,

        @Schema(
                description = "아티클 제목",
                example = "아티클 제목입니다."
        )
        @NotBlank(message = "아티클 제목은 필수입니다.")
        String title,

        @Schema(
                description = "아티클 상세 내용",
                example = "여기에 아티클의 상세 내용이 들어갑니다.",
                required = false
        )
        String content,

        @Schema(
                description = "아티클 썸네일 이미지 URI",
                example = "http://example.com/image.jpg"
        )
        @NotBlank(message = "아티클 썸네일 이미지 URI 필수입니다.")
        @JsonProperty("thumbnail_image_uri") String thumbnailImageUri
) { }
