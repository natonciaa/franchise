package co.com.seti.usecase.addbranch;

import co.com.seti.model.branch.Branch;
import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AddBranchUseCase {
    private final FranchiseRepository repo;
    private static final Logger logger = LogManager.getLogger(AddBranchUseCase.class);

    public Mono<Franchise> create(String franchiseId, String branchName) {
        return repo.findById(franchiseId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Franchise not found")))
                .map(fr -> {
                    var branches = fr.getBranches() == null ? new java.util.ArrayList<Branch>() : fr.getBranches();
                    branches.add(new Branch(java.util.UUID.randomUUID().toString(), branchName, new java.util.ArrayList<>()));
                    fr.setBranches(branches);
                    return fr;
                })
                .flatMap(repo::save)
                .doOnNext(f -> {
                    logger.info(String.format("Branch created", f.getName()));
                })
                .doOnError(err -> {
                    logger.error(err.getMessage(), err);
                })
                .doOnSuccess(f -> {
                    logger.info(String.format("The Branch creation process was completed successfully ", f.getName()));
                });
    }
}
