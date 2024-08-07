package darkoverload.itzip.feature.school.service;

import darkoverload.itzip.feature.school.code.RegionType;
import darkoverload.itzip.feature.school.code.SchoolType;
import darkoverload.itzip.feature.school.controller.response.SearchResponse;
import darkoverload.itzip.feature.school.domain.School;

import java.util.List;

public interface SchoolService {
    SearchResponse searchSchool(String schoolName);
}
