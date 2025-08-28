package co.com.seti.usecase.updatefranchisename;

import lombok.RequiredArgsConstructor;
import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateFranchiseNameUseCase {
    private final FranchiseRepository repo;

    public Mono<Franchise> update(String franchiseId, String newName) {
        return repo.findById(franchiseId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Franchise not found")))
                .flatMap(fr -> {
                    fr.setName(newName);
                    return repo.save(fr);
                });
    }
}
