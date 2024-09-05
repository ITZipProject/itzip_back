package darkoverload.itzip.feature.tech.domain;

import darkoverload.itzip.feature.tech.controller.response.GetTechResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tech {

    private Long id;

    private Long code;

    private String name;


    public GetTechResponse toGetTechResponse(){
        return GetTechResponse.builder()
                .id(this.id)
                .code(this.code)
                .name(this.name)
                .build();
    }
}
