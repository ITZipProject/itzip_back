package darkoverload.itzip.feature.tech.entity;

import darkoverload.itzip.feature.tech.domain.Tech;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tech_stacks")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TechEntity {

    @Id
    private Long id;

    private Long code;

    private String name;

    public Tech toDomain() {
        return Tech.builder()
                .id(this.id)
                .code(this.code)
                .name(this.name)
                .build();
    }

}
