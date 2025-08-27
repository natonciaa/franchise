package co.com.seti.api;

import co.com.seti.usecase.addbranch.AddBranchUseCase;
import co.com.seti.usecase.createfranchiseuc.CreateFranchiseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    @Bean RouterFunction<ServerResponse> routes(Handler h){
        return RouterFunctions.route()
                .path("/franchises", builder -> builder
                        .POST("", h::create)
                        .POST("/{franchiseId}/branches", h::addBranch)
                ).build();
    }

    @Bean Handler handler(
            CreateFranchiseUseCase c, AddBranchUseCase ab){
        return new Handler(c, ab);
    }
}

