package darkoverload.itzip.feature.school.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class SearchResponse {
    List<String> schoolList;

    @Builder
    public SearchResponse(List<String> schoolList) {
        this.schoolList = schoolList;
    }
}
