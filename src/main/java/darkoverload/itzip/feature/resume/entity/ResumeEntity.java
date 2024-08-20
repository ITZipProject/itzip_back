package darkoverload.itzip.feature.resume.entity;


import darkoverload.itzip.feature.resume.util.StringListConverter;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="resumes")
public class ResumeEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false, insertable = false)
    private String userId;

    private String email;

    private String imageUrl;

    @Column(length=50)
    private String subject;

    @Column(length=50)
    private String phone;

    @Column(length=5000)
    private String introduction;

    private String address;

    @Convert(converter = StringListConverter.class)
    private String links;
}
