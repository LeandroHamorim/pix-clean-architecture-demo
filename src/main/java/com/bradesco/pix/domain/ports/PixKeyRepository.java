package com.bradesco.pix.domain.ports;

import com.bradesco.pix.domain.entities.PixKey;

import java.util.List;
import java.util.Optional;

public interface PixKeyRepository {
    Optional<PixKey> findByKey(String key);
    List<PixKey> findByAccountNumberAndAgency(String accountNumber, String agency);
    PixKey save(PixKey pixKey);
    void delete(String key);
}
