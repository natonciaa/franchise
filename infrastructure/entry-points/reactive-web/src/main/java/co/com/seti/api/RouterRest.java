package co.com.seti.api;

import co.com.seti.usecase.addbranch.AddBranchUseCase;
import co.com.seti.usecase.addproduct.AddProductUseCase;
import co.com.seti.usecase.createfranchise.CreateFranchiseUseCase;
import co.com.seti.usecase.deleteproduct.DeleteProductUseCase;
import co.com.seti.usecase.topstockperbranch.TopStockPerBranchUseCase;
import co.com.seti.usecase.updatestock.UpdateStockUseCase;
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
                        .PATCH("/{franchiseId}/branches/{branchId}/products/{productId}/stock", h::updateStock)
                        .GET("/{franchiseId}/branches/top-product", h::topPerBranch)

                ).build();
    }

    @Bean
    Handler handler(
            CreateFranchiseUseCase c, AddBranchUseCase ab, AddProductUseCase ap, DeleteProductUseCase dp, UpdateStockUseCase us, TopStockPerBranchUseCase ts) {
        return new Handler(c, ab, ap, dp, us, ts);
    }
}

