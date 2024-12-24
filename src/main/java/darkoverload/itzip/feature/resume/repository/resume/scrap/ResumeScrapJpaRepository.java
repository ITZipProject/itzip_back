package darkoverload.itzip.feature.resume.repository.resume.scrap;


import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrap;
import darkoverload.itzip.feature.resume.repository.resume.scrap.custom.CustomScrapResumeRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResumeScrapJpaRepository extends JpaRepository<ResumeScrap, Long>, CustomScrapResumeRepository {

}
