package darkoverload.itzip.feature.school.entity;

import darkoverload.itzip.feature.school.code.EstType;
import darkoverload.itzip.feature.school.code.RegionType;
import darkoverload.itzip.feature.school.code.SchoolType;
import darkoverload.itzip.feature.school.domain.School;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="school_infos")
public class SchoolEntity {
    @Id
    @SequenceGenerator(
            name="SCHOOL_INFOS_SEQ_GENERATOR",
            sequenceName = "SCHOOL_SEQ",
            initialValue = 1,
            allocationSize = 50
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SCHOOL_INFOS_SEQ_GENERATOR")
    private Long id;

    @Column(name="school_name", length = 100)
    private String schoolName;

    private String gubun;

    @Enumerated(EnumType.STRING)
    @Column(name="school_type")
    private SchoolType schoolType;

    private String address;

    @Column(name="campus_name")
    private String campusName;

    @Enumerated(EnumType.STRING)
    @Column(name="est_type")
    private EstType estType;

    @Enumerated(EnumType.STRING)
    private RegionType region;


    public School convertDomain(){
        return School.builder()
                .schoolName(this.schoolName)
                .gubun(this.gubun)
                .schoolType(this.schoolType)
                .address(this.address)
                .campusName(this.campusName)
                .estType(this.estType)
                .region(this.region)
                .build();
    }
}
