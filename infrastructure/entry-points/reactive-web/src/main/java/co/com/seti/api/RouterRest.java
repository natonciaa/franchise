package co.com.seti.api;

import co.com.seti.usecase.addbranch.AddBranchUseCase;
import co.com.seti.usecase.addproduct.AddProductUseCase;
import co.com.seti.usecase.createfranchise.CreateFranchiseUseCase;
import co.com.seti.usecase.deleteproduct.DeleteProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    @Bean
    RouterFunction<ServerResponse> routes(Handler h) {
        return RouterFunctions.route()
                .path("/franchises", builder -> builder
                        .POST("", h::create)
                        .POST("/{franchiseId}/branches", h::addBranch)
                        .POST("/{franchiseId}/branches/{branchId}/products", h::addProduct)
                        .DELETE("/{franchiseId}/branches/{branchId}/products/{productId}", h::deleteProduct)
                ).build();
    }

    @Bean
    Handler handler(
            CreateFranchiseUseCase c, AddBranchUseCase ab, AddProductUseCase ap, DeleteProductUseCase dp) {
        return new Handler(c, ab, ap, dp);
    }
}

