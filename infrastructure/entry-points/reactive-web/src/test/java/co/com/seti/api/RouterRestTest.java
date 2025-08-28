package co.com.seti.api;

import co.com.seti.model.franchise.Franchise;
import co.com.seti.usecase.addbranch.AddBranchUseCase;
import co.com.seti.usecase.addproduct.AddProductUseCase;
import co.com.seti.usecase.createfranchise.CreateFranchiseUseCase;
import co.com.seti.usecase.deleteproduct.DeleteProductUseCase;
import co.com.seti.usecase.topstockperbranch.TopStockPerBranchUseCase;
import co.com.seti.usecase.updatestock.UpdateStockUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRest.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CreateFranchiseUseCase createFranchiseUC;
    @MockitoBean
    private AddBranchUseCase addBranchUC;
    @MockitoBean
    private AddProductUseCase addProductUC;
    @MockitoBean
    private DeleteProductUseCase deleteProductUC;
    @MockitoBean
    private UpdateStockUseCase updateStockUC;
    @MockitoBean
    private TopStockPerBranchUseCase topStockUC;

    @Test
    void shouldExposeCreateFranchise() {
        when(createFranchiseUC.create(any())).thenReturn(Mono.just(new Franchise("fid", "Fran1", new ArrayList<>())));

        webTestClient.post().uri("/franchises")
                .bodyValue(Map.of("name", "Fran1"))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void shouldExposeAddBranch() {
        when(addBranchUC.create(any(), any())).thenReturn(Mono.just(new Franchise("fid", "Fran1", new ArrayList<>())));

        webTestClient.post().uri("/franchises/fid/branches")
                .bodyValue(Map.of("name", "Branch1"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldExposeAddProduct() {
        when(addProductUC.create(any(), any(), any(), anyInt()))
                .thenReturn(Mono.just(new Franchise("fid", "Fran1", new ArrayList<>())));

        webTestClient.post().uri("/franchises/fid/branches/bid/products")
                .bodyValue(Map.of("name", "Prod1", "stock", 5))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldExposeDeleteProduct() {
        when(deleteProductUC.delete(any(), any(), any()))
                .thenReturn(Mono.just(new Franchise("fid", "Fran1", new ArrayList<>())));

        webTestClient.delete().uri("/franchises/fid/branches/bid/products/pid")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void shouldExposeUpdateStock() {
        when(updateStockUC.updateStock(any(), any(), any(), anyInt()))
                .thenReturn(Mono.just(new Franchise("fid", "Fran1", new ArrayList<>())));

        webTestClient.patch().uri("/franchises/fid/branches/bid/products/pid/stock")
                .bodyValue(Map.of("stock", 99))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldExposeTopProductPerBranch() {
        when(topStockUC.getTopStock(any())).thenReturn(Flux.just(new TopStockPerBranchUseCase.TopProductPerBranch("pid", "Prod1", 50)));

        webTestClient.get().uri("/franchises/fid/branches/top-product")
                .exchange()
                .expectStatus().isOk();
    }
}
