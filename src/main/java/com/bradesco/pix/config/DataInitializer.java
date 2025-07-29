package com.bradesco.pix.config;

import com.bradesco.pix.domain.entities.Account;
import com.bradesco.pix.domain.ports.AccountRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public class DataInitializer {
    private final AccountRepository accountRepository;

    public DataInitializer(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    public void initializeData() {
        if (accountRepository.findByAccountNumberAndAgency("12345", "0001").isEmpty()) {

            Account account1 = new Account(
                    "12345",
                    "0001",
                    "12345678901",
                    "Bruno Bega",
                    new BigDecimal("1000.00")
            );
            accountRepository.save(account1);

            Account account2 = new Account(
                    "67890",
                    "0001",
                    "98765432109",
                    "Maria Santos",
                    new BigDecimal("500.00")
            );
            accountRepository.save(account2);

            Account account3 = new Account(
                    "11111",
                    "0001",
                    "11111111111",
                    "Pedro Oliveira",
                    new BigDecimal("2000.00")
            );
            accountRepository.save(account3);
            System.out.println("Dados de teste inicializados com sucesso.");
        } else {
            System.out.println("Dados de teste já existem no banco.");
        }
    }
}
