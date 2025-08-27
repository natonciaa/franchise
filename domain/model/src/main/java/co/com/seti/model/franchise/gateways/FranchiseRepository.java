package co.com.seti.model.franchise.gateways;

import co.com.seti.model.franchise.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise f);
    Mono<Franchise> findById(String id);
    Mono<Boolean> existsByName(String name);
}