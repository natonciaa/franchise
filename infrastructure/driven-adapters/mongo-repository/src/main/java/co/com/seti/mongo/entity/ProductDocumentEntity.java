package co.com.seti.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDocumentEntity {
    private String id;
    private String name;
    private int stock;
}