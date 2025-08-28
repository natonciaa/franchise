package co.com.seti.mongo;

import co.com.seti.model.branch.Branch;
import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import co.com.seti.model.product.Product;
import co.com.seti.mongo.entity.BranchDocumentEntity;
import co.com.seti.mongo.entity.FranchiseDocumentEntity;
import co.com.seti.mongo.entity.ProductDocumentEntity;
import co.com.seti.mongo.repository.FranchiseReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MongoRepositoryAdapter implements FranchiseRepository {

    private final FranchiseReactiveRepository repo;

    public Mono<Franchise> save(Franchise f) {
        return repo.save(toDoc(f)).map(this::toDomain);
    }

    public Mono<Franchise> findById(String id) {
        return repo.findById(id).map(this::toDomain);
    }

    public Mono<Boolean> existsByName(String name) {
        return repo.existsByName(name);
    }

    public Mono<Franchise> findByName(String name) {
        return repo.findByName(name).map(this::toDomain);
    }

    public Flux<Franchise> findAll() {
        return repo.findAll().map(this::toDomain);
    }

    private Franchise toDomain(FranchiseDocumentEntity d) {
        var branches = d.getBranches() == null ? java.util.List.<Branch>of()
                : d.getBranches().stream().map(b -> new Branch(b.getId(), b.getName(),
                b.getProducts() == null ? java.util.List.of() :
                        b.getProducts().stream().map(p -> new Product(p.getId(), p.getName(), p.getStock())).toList()
        )).toList();
        return new Franchise(d.getId().toString(), d.getName(), new java.util.ArrayList<>(branches));
    }

    private FranchiseDocumentEntity toDoc(Franchise f) {
        var branches = f.getBranches() == null ? java.util.List.<BranchDocumentEntity>of()
                : f.getBranches().stream().map(b -> new BranchDocumentEntity(b.getId(), b.getName(),
                b.getProducts() == null ? java.util.List.of() :
                        b.getProducts().stream().map(p -> new ProductDocumentEntity(p.getId(), p.getName(), p.getStock())).toList()
        )).toList();
        return new FranchiseDocumentEntity(f.getId(), f.getName(), new java.util.ArrayList<>(branches));
    }

}

