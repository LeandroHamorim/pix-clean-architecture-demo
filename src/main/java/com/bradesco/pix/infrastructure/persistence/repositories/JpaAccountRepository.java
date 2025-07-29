package com.bradesco.pix.infrastructure.persistence.repositories;

import com.bradesco.pix.domain.entities.Account;
import com.bradesco.pix.domain.ports.AccountRepository;
import com.bradesco.pix.infrastructure.adapters.rest.mappers.AccountMapper;
import com.bradesco.pix.infrastructure.persistence.entities.AccountEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class JpaAccountRepository implements AccountRepository {
    private final SpringAccountRepository springRepository;
    private final AccountMapper mapper;

    public JpaAccountRepository(SpringAccountRepository springRepository, AccountMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Account> findByAccountNumberAndAgency(String accountNumber, String agency) {
        return springRepository.findByAccountNumberAndAgency(accountNumber, agency)
                .map(mapper::toDomain);
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = mapper.toEntity(account);
        AccountEntity saved = springRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void update(Account account) {
        AccountEntity entityToUpdate = springRepository.findByAccountNumberAndAgency(account.getAccountNumber(), account.getAgency())
                .orElseThrow(() -> new IllegalStateException("Account to update not found: " + account.getAccountNumber()));

        mapper.updateEntityFromDomain(account, entityToUpdate);

        springRepository.save(entityToUpdate);
    }

}