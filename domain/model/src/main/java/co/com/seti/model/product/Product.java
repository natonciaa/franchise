package co.com.seti.model.product;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String name;
    private int stock;
}
