package darkoverload.itzip.feature.algorithm.service.tag;

import darkoverload.itzip.feature.algorithm.controller.response.SolvedTagResponse;
import darkoverload.itzip.feature.algorithm.entity.ProblemTagEntity;
import darkoverload.itzip.feature.algorithm.repository.tag.ProblemTagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FindAllTagsServiceImplTest {

    @Mock
    private ProblemTagRepository problemTagRepository;

    @InjectMocks
    private FindAllTagsServiceImpl findAllTagsService;

    @Test
    void 모든_태그를_가져오는_테스트() {
        List<ProblemTagEntity> allTags = List.of(mock(ProblemTagEntity.class), mock(ProblemTagEntity.class));
        when(problemTagRepository.findAll()).thenReturn(allTags);

        SolvedTagResponse response = findAllTagsService.findSolvedTags(false);

        assertNotNull(response);
        assertEquals(allTags.size(), response.getTags().size());
    }

    @Test
    void 추천된_태그만_가져오는_테스트() {
        List<ProblemTagEntity> recommendedTagEntities = List.of(mock(ProblemTagEntity.class), mock(ProblemTagEntity.class));
        when(problemTagRepository.findAllById(anyList())).thenReturn(recommendedTagEntities);

        SolvedTagResponse response = findAllTagsService.findSolvedTags(true);

        assertNotNull(response);
        assertEquals(recommendedTagEntities.size(), response.getTags().size());
    }
}