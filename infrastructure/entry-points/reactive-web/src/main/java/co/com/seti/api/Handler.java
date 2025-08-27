package co.com.seti.api;

import co.com.seti.api.dto.AddProductReq;
import co.com.seti.model.branch.Branch;
import co.com.seti.model.franchise.Franchise;
import co.com.seti.usecase.addbranch.AddBranchUseCase;
import co.com.seti.usecase.addproduct.AddProductUseCase;
import co.com.seti.usecase.createfranchise.CreateFranchiseUseCase;
import co.com.seti.usecase.deleteproduct.DeleteProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
public class Handler {
    private final CreateFranchiseUseCase createFranchiseUC;
    private final AddBranchUseCase addBranchUC;
    private final AddProductUseCase addProductUC;
    private final DeleteProductUseCase deleteProductUC;
    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Franchise.class)
                .flatMap(r -> createFranchiseUC.create(r.getName()))
                .flatMap(fr -> ServerResponse.created(URI.create("/franchises/" + fr.getId())).bodyValue(fr))
                .doOnNext(res -> log.info("create franchise onNext"))
                .onErrorResume(IllegalArgumentException.class,
                        e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> addBranch(ServerRequest req) {
        String id = req.pathVariable("franchiseId");
        return req.bodyToMono(Branch.class)
                .flatMap(r -> addBranchUC.create(id, r.getName()))
                .flatMap(fr -> ServerResponse.ok().bodyValue(fr))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> addProduct(ServerRequest req) {
        String fid = req.pathVariable("franchiseId");
        String bid = req.pathVariable("branchId");
        return req.bodyToMono(AddProductReq.class)
                .flatMap(r -> addProductUC.create(fid, bid, r.name(), r.stock()))
                .flatMap(fr -> ServerResponse.ok().bodyValue(fr))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest req) {
        String fid = req.pathVariable("franchiseId");
        String bid = req.pathVariable("branchId");
        String pid = req.pathVariable("productId");
        return deleteProductUC.delete(fid, bid, pid)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.status(404).bodyValue(e.getMessage()));
    }
}
