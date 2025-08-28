package co.com.seti.usecase.updatestock;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateStockUseCaseTest {

    @Mock
    FranchiseRepository repo;

    @InjectMocks
    UpdateStockUseCase useCase;

    @Test
    void shouldUpdateStock() {
        Product p = new Product("pid", "Prod1", 5);
        Branch branch = new Branch("bid", "Branch1", new ArrayList<>(List.of(p)));
        Franchise franchise = new Franchise("fid", "Fran1", new ArrayList<>(List.of(branch)));

        when(repo.findById("fid")).thenReturn(Mono.just(franchise));
        when(repo.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(useCase.updateStock("fid", "bid", "pid", 99))
                .expectNextMatches(fr -> fr.getBranches().get(0).getProducts().get(0).getStock() == 99)
                .verifyComplete();
    }
}
