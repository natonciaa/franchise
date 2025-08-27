package co.com.seti.mongo;

import co.com.seti.mongo.entity.FranchiseDocumentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface MongoDBRepository extends ReactiveMongoRepository<FranchiseDocumentEntity, String>, ReactiveQueryByExampleExecutor<FranchiseDocumentEntity> {
}
