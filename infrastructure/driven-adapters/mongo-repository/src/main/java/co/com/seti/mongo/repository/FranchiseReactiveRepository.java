package co.com.seti.mongo.repository;

import co.com.seti.mongo.entity.FranchiseDocumentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FranchiseReactiveRepository extends ReactiveMongoRepository<FranchiseDocumentEntity, String> {
    reactor.core.publisher.Mono<Boolean> existsByName(String name);

    reactor.core.publisher.Mono<FranchiseDocumentEntity> findByName(String name);
}
