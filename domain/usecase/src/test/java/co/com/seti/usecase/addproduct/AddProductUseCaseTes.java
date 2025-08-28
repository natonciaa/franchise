package co.com.seti.usecase.addproduct;

import co.com.seti.model.branch.Branch;
import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
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
public class AddProductUseCaseTes {


        @Mock
        FranchiseRepository repo;

        @InjectMocks
        AddProductUseCase useCase;

        @Test
        void shouldAddProduct() {
            Branch branch = new Branch("bid", "Branch1", new ArrayList<>());
            Franchise franchise = new Franchise("fid", "Fran1", new ArrayList<>(List.of(branch)));

            when(repo.findById("fid")).thenReturn(Mono.just(franchise));
            when(repo.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

            StepVerifier.create(useCase.create("fid", "bid", "Prod1", 10))
                    .expectNextMatches(fr -> fr.getBranches().get(0).getProducts().size() == 1)
                    .verifyComplete();
        }

        @Test
        void shouldFailWhenBranchNotFound() {
            Franchise franchise = new Franchise("fid", "Fran1", new ArrayList<>());

            when(repo.findById("fid")).thenReturn(Mono.just(franchise));

            StepVerifier.create(useCase.create("fid", "bid", "Prod1", 10))
                    .expectError(IllegalArgumentException.class)
                    .verify();
        }


}
