package co.com.seti.mongo.config;

import co.com.seti.model.franchise.gateways.FranchiseRepository;
import co.com.seti.mongo.MongoRepositoryAdapter;
import co.com.seti.mongo.repository.FranchiseReactiveRepository;
import co.com.seti.usecase.addbranch.AddBranchUseCase;
import co.com.seti.usecase.addproduct.AddProductUseCase;
import co.com.seti.usecase.createfranchise.CreateFranchiseUseCase;
import co.com.seti.usecase.deleteproduct.DeleteProductUseCase;
import co.com.seti.usecase.topstockperbranch.TopStockPerBranchUseCase;
import co.com.seti.usecase.updatestock.UpdateStockUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    FranchiseRepository franchiseRepository(FranchiseReactiveRepository springRepo) {
        return new MongoRepositoryAdapter(springRepo);
    }

    @Bean
    CreateFranchiseUseCase createFranchiseUC(FranchiseRepository r) {
        return new CreateFranchiseUseCase(r);
    }

    @Bean
    AddBranchUseCase addBranchUC(FranchiseRepository r) {
        return new AddBranchUseCase(r);
    }

    @Bean
    AddProductUseCase addProductUC(FranchiseRepository r) {
        return new AddProductUseCase(r);
    }

    @Bean
    DeleteProductUseCase deleteProductUC(FranchiseRepository r) {
        return new DeleteProductUseCase(r);
    }

    @Bean
    UpdateStockUseCase updateStockUC(FranchiseRepository r) {
        return new UpdateStockUseCase(r);
    }

    @Bean
    TopStockPerBranchUseCase topStockPerBranchUC(FranchiseRepository r) {
        return new TopStockPerBranchUseCase(r);
    }
}

