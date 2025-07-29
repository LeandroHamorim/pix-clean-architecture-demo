package com.bradesco.pix.infrastructure.persistence.repositories;

import com.bradesco.pix.infrastructure.persistence.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpringTransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByTransactionId(String transactionId);
    List<TransactionEntity> findBySourceAccountNumberAndSourceAgency(String accountNumber, String agency);
}
