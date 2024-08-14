package darkoverload.itzip.feature.school.entity;


import darkoverload.itzip.feature.school.code.EstType;
import darkoverload.itzip.feature.school.code.RegionType;
import darkoverload.itzip.feature.school.code.SchoolType;
import darkoverload.itzip.feature.school.domain.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.suggest.Completion;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName="school_infos")
public class SchoolDocument {

    @Id
    private String id;

    @CompletionField(maxInputLength = 100)
    private Completion schoolNameSuggest;

    @Field(type= FieldType.Text, name = "school_name")
    private String schoolName;

    @Field(type = FieldType.Text)
    private String gubun;

    @Field(type = FieldType.Keyword, name = "school_type")
    private SchoolType schoolType;

    @Field(type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Text, name = "campus_name")
    private String campusName;

    @Field(type = FieldType.Keyword, name = "est_type")
    private EstType estType;

    @Field(type = FieldType.Keyword)
    private RegionType region;

   public School convertDomain() {
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
