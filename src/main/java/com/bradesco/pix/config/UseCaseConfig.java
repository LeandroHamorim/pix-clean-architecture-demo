package com.bradesco.pix.config;

import com.bradesco.pix.application.usecases.CreatePixKeyUseCase;
import com.bradesco.pix.application.usecases.ExecutePixTransferUseCase;
import com.bradesco.pix.domain.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreatePixKeyUseCase createPixKeyUseCase(PixKeyRepository pixKeyRepository,
                                                   AccountRepository accountRepository) {
        return new CreatePixKeyUseCase(pixKeyRepository, accountRepository);
    }

    @Bean
    public ExecutePixTransferUseCase executePixTransferUseCase(AccountRepository accountRepository,
                                                               PixKeyRepository pixKeyRepository,
                                                               TransactionRepository transactionRepository,
                                                               BacenService bacenService,
                                                               TransactionManager transactionManager) {
        return new ExecutePixTransferUseCase(accountRepository, pixKeyRepository,
                transactionRepository, bacenService,transactionManager);
    }
}
