package co.com.seti.model.branch;

import co.com.seti.model.product.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    private String id;
    private String name;
    private List<Product> products = new ArrayList<>();
}

