package co.com.seti.model.franchise;

import co.com.seti.model.branch.Branch;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Franchise {
    private String id;
    private String name;
    private List<Branch> branches = new ArrayList<>();
}
