package co.com.seti.usecase.createfranchiseuc;

import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateFranchiseUseCase {

    private final FranchiseRepository repo;

    public Mono<Franchise> create(String name) {
        return repo.existsByName(name)
                .flatMap(exists -> exists
                        ? Mono.error(new IllegalArgumentException("Franchise name exists"))
                        : repo.save(new Franchise(null, name, null)));
    }
}
