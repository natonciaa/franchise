package co.com.seti.usecase.topstockperbranch;

import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import co.com.seti.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

@RequiredArgsConstructor
public class TopStockPerBranchUseCase {

    private final FranchiseRepository repo;

    public Flux<TopProductPerBranch> getTopStock(String franId) {
        return repo.findById(franId)
                .doOnNext(fr -> System.out.println("Franquicia encontrada: " + fr.getId()))
                .flatMapMany(fr -> Flux.fromIterable(fr.getBranches()))
                .flatMap(branch -> {
                    var top = branch.getProducts().stream().max(Comparator.comparingInt(product -> product.getStock()));
                    return Mono.zip(Mono.just(branch), Mono.justOrEmpty(top))
                            .map(tuple -> new TopProductPerBranch(tuple.getT1().getName(), tuple.getT2().getName(),tuple.getT2().getStock()));
                });

    }

    @lombok.Value
    public static class TopProductPerBranch {
        String branchName; String name; int product;
    }
}
