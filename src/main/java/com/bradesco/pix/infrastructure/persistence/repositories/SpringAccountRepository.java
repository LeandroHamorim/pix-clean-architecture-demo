package com.bradesco.pix.infrastructure.persistence.repositories;

import com.bradesco.pix.infrastructure.persistence.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringAccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByAccountNumberAndAgency(String accountNumber, String agency);
}
