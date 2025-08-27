package co.com.seti.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDocumentEntity {
    private String id;
    private String name;
    private java.util.List<ProductDocumentEntity> products = new java.util.ArrayList<>();
}

