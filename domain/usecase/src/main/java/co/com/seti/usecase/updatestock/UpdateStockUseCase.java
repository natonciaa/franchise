package co.com.seti.usecase.updatestock;

import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateStockUseCase {
    private final FranchiseRepository repo;

    public Mono<Franchise> updateStock(String franId, String branchId, String prodId, int newStock) {
        return repo.findById(franId)
                .doOnNext(fr -> System.out.println("Franquicia encontrada: " + fr.getId()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Franchise not found")))
                .flatMap(fr -> {
                    var branch = fr.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
                    var prod = branch.getProducts().stream()
                                    .filter(pr -> pr.getId().equals(prodId))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    prod.setStock(newStock);

                    return repo.save(fr)
                            .doOnNext(saved -> System.out.println("Producto guardado con nuevo stock: " + saved.getId()));
                });
    }
}
