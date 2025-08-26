package co.com.seti.usecase.createfranchiseuc;

import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import reactor.core.publisher.Mono;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class CreateFranchiseUseCase {

    private final FranchiseRepository repo;
    private static final Logger logger = LogManager.getLogger(CreateFranchiseUseCase.class);

    public Mono<Franchise> create(String name) {
        return repo.existsByName(name)
                .flatMap(exists -> exists
                        ? Mono.error(new IllegalArgumentException("Franchise name exists"))
                        : repo.save(new Franchise(null, name, null)))
                .doOnNext(f -> {
                    logger.info(String.format("Franchise created", f.getName()));
                })
                .doOnError(err -> {
                    logger.error(err.getMessage(), err);
                })
                .doOnSuccess(f -> {
                    logger.info(String.format("The franchise creation process was completed successfully ", f.getName()));
                });
    }
}
