package darkoverload.itzip.resume.entity;

import darkoverload.itzip.resume.code.EstType;
import darkoverload.itzip.resume.code.RegionType;
import darkoverload.itzip.resume.code.SchoolType;
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

}
