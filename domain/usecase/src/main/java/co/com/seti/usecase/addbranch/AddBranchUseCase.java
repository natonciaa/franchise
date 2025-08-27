package co.com.seti.usecase.addbranch;

import co.com.seti.model.branch.Branch;
import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AddBranchUseCase {
    private final FranchiseRepository repo;

    public Mono<Franchise> create(String franchiseId, String branchName) {
        return repo.findById(franchiseId)
                .doOnNext(fr -> System.out.println("Franquicia encontrada: " + fr.getId()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Franchise not found")))
                .map(fr -> {
                    var branches = fr.getBranches() == null ? new java.util.ArrayList<Branch>() : fr.getBranches();
                    branches.add(new Branch(java.util.UUID.randomUUID().toString(), branchName, new java.util.ArrayList<>()));
                    fr.setBranches(branches);
                    return fr;
                })
                .flatMap(repo::save);
    }
}
