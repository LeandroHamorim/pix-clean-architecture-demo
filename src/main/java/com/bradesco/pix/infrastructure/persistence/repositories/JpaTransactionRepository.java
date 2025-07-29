package com.bradesco.pix.infrastructure.persistence.repositories;

import com.bradesco.pix.domain.entities.PixTransaction;
import com.bradesco.pix.domain.ports.TransactionRepository;
import com.bradesco.pix.infrastructure.persistence.entities.TransactionEntity;
import com.bradesco.pix.infrastructure.adapters.rest.mappers.TransactionMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaTransactionRepository implements TransactionRepository {
    private final SpringTransactionRepository springRepository;
    private final TransactionMapper mapper;

    public JpaTransactionRepository(SpringTransactionRepository springRepository, TransactionMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public PixTransaction save(PixTransaction transaction) {
        var entity = mapper.toEntity(transaction);
        var saved = springRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<PixTransaction> findById(String transactionId) {
        return springRepository.findByTransactionId(transactionId)
                .map(mapper::toDomain);
    }

    @Override
    public List<PixTransaction> findBySourceAccount(String accountNumber, String agency) {
        return springRepository.findBySourceAccountNumberAndSourceAgency(accountNumber, agency)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(PixTransaction transaction) {
        TransactionEntity entityToUpdate = springRepository.findByTransactionId(transaction.getTransactionId())
                .orElseThrow(() -> new IllegalStateException("Transaction to update not found: " + transaction.getTransactionId()));

        mapper.updateEntityFromDomain(transaction, entityToUpdate);

        springRepository.save(entityToUpdate);
    }

}