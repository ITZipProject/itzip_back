package darkoverload.itzip.feature.tech.service;

import darkoverload.itzip.feature.tech.controller.response.GetTechResponse;
import darkoverload.itzip.feature.tech.domain.Tech;

import java.util.List;

public interface TechService {
    List<Tech> getTechInfo();

}
