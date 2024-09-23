package darkoverload.itzip.feature.tech.controller.response;


import lombok.Builder;

@Builder
public record GetTechResponse(Long id, Long code, String name) {

}
