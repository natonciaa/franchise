package co.com.seti.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("franchises")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FranchiseDocumentEntity {
    @Id
    private String id;
    private String name;
    private java.util.List<BranchDocumentEntity> branches = new java.util.ArrayList<>();
}
