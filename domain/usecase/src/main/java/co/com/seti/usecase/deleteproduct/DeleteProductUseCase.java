package co.com.seti.usecase.deleteproduct;

import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RequiredArgsConstructor
public class DeleteProductUseCase {

    private final FranchiseRepository repo;

    public Mono<Franchise> delete(String franId, String branchId, String prodId) {
        return repo.findById(franId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Franchise not found")))
                .flatMap(fr -> {
                    var branch = fr.getBranches().stream().filter(b -> b.getId().equals(branchId)).findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
                    if (!(branch.getProducts() instanceof ArrayList)) {
                        branch.setProducts(new ArrayList<>(branch.getProducts()));
                    }

                    boolean removed = branch.getProducts().removeIf(p -> p.getId().equals(prodId));
                    if (!removed) return Mono.error(new IllegalArgumentException("Product not found"));
                    return repo.save(fr);
                });
    }
}
