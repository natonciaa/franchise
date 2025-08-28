package co.com.seti.usecase.topstockperbranch;

import co.com.seti.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Comparator;


@RequiredArgsConstructor
public class TopStockPerBranchUseCase {

    private final FranchiseRepository repo;

    public Flux<TopProductPerBranch> getTopStock(String franId) {
        return repo.findById(franId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Franquicia no encontrada")))
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
