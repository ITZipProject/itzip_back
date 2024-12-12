//package darkoverload.itzip.feature.resume.mock;
//
//import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
//import darkoverload.itzip.feature.resume.service.resume.port.qualification.QualificationReadRepository;
//import darkoverload.itzip.feature.resume.service.resume.port.qualification.QualificationCommandRepository;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//import java.util.concurrent.atomic.AtomicLong;
//
//public class FakeQualificationCommandRepository implements QualificationCommandRepository, QualificationReadRepository {
//    private final AtomicLong autoGeneratedId = new AtomicLong(0);
//    private final List<Qualification> data = Collections.synchronizedList(new ArrayList<>());
//
//    @Override
//    public List<Qualification> findAllByResumeId(Long resumeId) {
//        return data.stream()
//                .filter(qualification -> Objects.equals(qualification.getResume().getResumeId(), resumeId))
//                .toList();
//    }
//
//    @Override
//    public List<Qualification> update(List<Qualification> qualifications) {
//        return saveAll(qualifications);
//    }
//
//    @Override
//    public Qualification save(Qualification qualification) {
//        if (qualification.getQualificationId() == null || qualification.getQualificationId() == 0) {
//            Qualification newQualification = Qualification.builder()
//                    .qualificationId(autoGeneratedId.incrementAndGet())
//                    .resume(qualification.getResume())
//                    .qualificationDate(qualification.getQualificationDate())
//                    .name(qualification.getName())
//                    .organization(qualification.getOrganization())
//                    .build();
//            data.add(newQualification);
//            return newQualification;
//        }
//        data.removeIf(item -> Objects.equals(item.getQualificationId(), qualification.getQualificationId()));
//        data.add(qualification);
//
//        return qualification;
//    }
//
//    @Override
//    public List<Qualification> saveAll(List<Qualification> qualifications) {
//        return qualifications.stream().map(this::save).toList();
//    }
//
//    @Override
//    public void deleteAllById(List<Long> ids) {
//        for (Long id : ids) {
//            data.removeIf(item -> Objects.equals(item.getQualificationId(), id));
//        }
//    }
//
//    @Override
//    public void deleteAllQualifications(List<Qualification> deleteQualifications) {
//        deleteQualifications.stream().map(Qualification::getQualificationId)
//                .forEach(id -> data.removeIf(qualification -> qualification.getQualificationId().equals(id)));
//    }
//
//}
