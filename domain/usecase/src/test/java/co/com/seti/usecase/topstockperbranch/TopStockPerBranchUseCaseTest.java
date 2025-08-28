package co.com.seti.usecase.topstockperbranch;

import co.com.seti.model.branch.Branch;
import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import co.com.seti.model.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopStockPerBranchUseCaseTest {

    @Mock
    FranchiseRepository repo;

    @InjectMocks
    TopStockPerBranchUseCase useCase;

    @Test
    void shouldReturnTopProducts() {
        Product p1 = new Product("p1", "Prod1", 5);
        Product p2 = new Product("p2", "Prod2", 20);
        Branch b1 = new Branch("b1", "Branch1", List.of(p1, p2));
        Franchise franchise = new Franchise("fid", "Fran1", new ArrayList<>(List.of(b1)));

        when(repo.findById("fid")).thenReturn(Mono.just(franchise));

        StepVerifier.create(useCase.getTopStock("fid"))
                .expectNextMatches(tp ->
                        tp.getBranchName().equals("Branch1") &&
                                tp.getName().equals("Prod2") &&
                                tp.getProduct() == 20
                )
                .verifyComplete();    }

    @Test
    void shouldFailWhenFranchiseNotFound() {
        when(repo.findById("fid")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.getTopStock("fid"))
                .expectError(IllegalArgumentException.class)
                .verify();
    }
}
