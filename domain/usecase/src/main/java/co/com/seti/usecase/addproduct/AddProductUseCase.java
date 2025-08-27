package co.com.seti.usecase.addproduct;

import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import co.com.seti.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
public class AddProductUseCase {
    private final FranchiseRepository repo;

    public Mono<Franchise> create(String franId, String branchId, String prodName, int stock) {
        return repo.findById(franId)
                .doOnNext(fr -> System.out.println("Franquicia encontrada: " + fr.getId()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Franchise not found")))
                .flatMap(fr -> {
                    var branch = fr.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Branch not found"));

                    if (!(branch.getProducts() instanceof ArrayList)) {
                        branch.setProducts(new ArrayList<>(branch.getProducts()));
                    }

                    branch.getProducts().add(new Product(UUID.randomUUID().toString(), prodName, stock));

                    return repo.save(fr)
                            .doOnNext(saved -> System.out.println("Guardado franquicia con nuevo producto: " + saved.getId()));
                });
    }
}
