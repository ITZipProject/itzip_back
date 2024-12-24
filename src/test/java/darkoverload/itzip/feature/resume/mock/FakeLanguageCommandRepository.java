//package darkoverload.itzip.feature.resume.mock;
//
//import darkoverload.itzip.feature.resume.domain.language.Language;
//import darkoverload.itzip.feature.resume.service.resume.port.language.LanguageReadRepository;
//import darkoverload.itzip.feature.resume.service.resume.port.language.LanguageCommandRepository;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//import java.util.concurrent.atomic.AtomicLong;
//
//public class FakeLanguageCommandRepository implements LanguageCommandRepository, LanguageReadRepository {
//
//    private final AtomicLong autoGeneratedId = new AtomicLong(0);
//    private final List<Language> data = Collections.synchronizedList(new ArrayList<>());
//
//    @Override
//    public List<Language> findAllByResumeId(Long resumeId) {
//        return data.stream()
//                .filter(language -> Objects.equals(language.getResume().getResumeId(), resumeId))
//                .toList();
//    }
//
//    @Override
//    public List<Language> update(List<Language> languages) {
//        return languages;
//    }
//
//    @Override
//    public Language save(Language language) {
//        if (language.getLanguageId() == null || language.getLanguageId() == 0) {
//            Language newLanguage = Language.builder()
//                    .languageId(autoGeneratedId.incrementAndGet())
//                    .resume(language.getResume())
//                    .score(language.getScore())
//                    .name(language.getName())
//                    .acquisitionDate(language.getAcquisitionDate())
//                    .build();
//
//            data.add(newLanguage);
//            return newLanguage;
//        }
//
//        data.removeIf(item -> Objects.equals(item.getLanguageId(), language.getLanguageId()));
//        data.add(language);
//        return language;
//    }
//
//    @Override
//    public List<Language> saveAll(List<Language> languages) {
//        return languages.stream().map(this::save).toList();
//    }
//
//    @Override
//    public void deleteAllById(List<Long> ids) {
//        for (Long id : ids) {
//            data.removeIf(item -> Objects.equals(item.getLanguageId(), id));
//        }
//    }
//
//    @Override
//    public void deleteAllLanguages(List<Language> deleteLanguages) {
//        deleteLanguages.stream().map(Language::getLanguageId)
//                .forEach(id -> data.removeIf(language -> language.getLanguageId().equals(id)));
//    }
//
//}