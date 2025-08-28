package co.com.seti.usecase.addbranch;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddBranchUseCaseTest {

        @Mock
        FranchiseRepository repo;

        @InjectMocks
        AddBranchUseCase useCase;

        @Test
        void shouldAddBranch() {
            Franchise franchise = new Franchise("fid", "Fran1", new ArrayList<>());
            when(repo.findById("fid")).thenReturn(Mono.just(franchise));
            when(repo.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

            StepVerifier.create(useCase.create("fid", "branch1"))
                    .expectNextMatches(fr -> fr.getBranches().size() == 1)
                    .verifyComplete();

            verify(repo).save(any());
        }

        @Test
        void shouldFailWhenFranchiseNotFound() {
            when(repo.findById("fid")).thenReturn(Mono.empty());

            StepVerifier.create(useCase.create("fid", "branch1"))
                    .expectError(IllegalArgumentException.class)
                    .verify();
        }
    }

