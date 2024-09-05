package darkoverload.itzip.feature.tech.service;

import darkoverload.itzip.feature.tech.domain.Tech;
import darkoverload.itzip.feature.tech.entity.TechEntity;
import darkoverload.itzip.feature.tech.repository.TechRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechServiceImpl implements TechService{

    private final TechRepository repository;


    @Override
    public List<Tech> getTechInfo() {

        return repository.findAll().stream().map(TechEntity::toDomain).collect(Collectors.toList());
    }
}
