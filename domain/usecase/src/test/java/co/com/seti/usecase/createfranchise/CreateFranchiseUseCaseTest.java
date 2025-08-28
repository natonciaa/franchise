package co.com.seti.usecase.createfranchise;

import co.com.seti.model.franchise.Franchise;
import co.com.seti.model.franchise.gateways.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateFranchiseUseCaseTest {

    FranchiseRepository repo = Mockito.mock(FranchiseRepository.class);
        CreateFranchiseUseCase uc = new CreateFranchiseUseCase(repo);

        @Test
        void createsWhenNotExists() {
            Mockito.when(repo.existsByName("Acme")).thenReturn(Mono.just(false));
            Mockito.when(repo.save(Mockito.any())).thenAnswer(inv -> {
                Franchise f = inv.getArgument(0); f.setId("id1"); return Mono.just(f);
            });

            StepVerifier.create(uc.create("Acme"))
                    .assertNext(f -> assertEquals("Acme", f.getName()))
                    .verifyComplete();

            Mockito.verify(repo).existsByName("Acme");
            Mockito.verify(repo).save(Mockito.any());
        }

        @Test
        void errorsWhenNameExists() {
            Mockito.when(repo.existsByName("Acme")).thenReturn(Mono.just(true));
            StepVerifier.create(uc.create("Acme"))
                    .expectError(IllegalArgumentException.class)
                    .verify();
        }
    }

