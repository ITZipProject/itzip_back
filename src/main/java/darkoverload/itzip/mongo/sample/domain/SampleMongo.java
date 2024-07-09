package darkoverload.itzip.mongo.sample.domain;


import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "sample_mongo")
public class SampleMongo {
    @Id
    private String id;

    private String name;

    public SampleMongo() {
    }

    public SampleMongo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void changeName(String name) {
        this.name=name;
    }
}
