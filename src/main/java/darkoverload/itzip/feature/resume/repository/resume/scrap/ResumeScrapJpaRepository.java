package darkoverload.itzip.feature.resume.repository.resume.scrap;


import darkoverload.itzip.feature.resume.entity.resume.ResumeScrapEntity;
import darkoverload.itzip.feature.resume.repository.resume.scrap.custom.CustomScrapResumeRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResumeScrapJpaRepository extends JpaRepository<ResumeScrapEntity, Long>, CustomScrapResumeRepository {

}
