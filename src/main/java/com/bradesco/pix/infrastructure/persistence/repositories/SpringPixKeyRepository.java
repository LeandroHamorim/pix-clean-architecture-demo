package com.bradesco.pix.infrastructure.persistence.repositories;

import com.bradesco.pix.infrastructure.persistence.entities.PixKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringPixKeyRepository extends JpaRepository<PixKeyEntity, Long> {
    Optional<PixKeyEntity> findByKeyValue(String keyValue);
    List<PixKeyEntity> findByAccountNumberAndAgency(String accountNumber, String agency);

    @Modifying
    @Transactional
    @Query("DELETE FROM PixKeyEntity p WHERE p.keyValue = :keyValue")
    void deleteByKeyValue(@Param("keyValue") String keyValue);
}
