package com.bradesco.pix.infrastructure.persistence.repositories;

import com.bradesco.pix.domain.entities.PixKey;
import com.bradesco.pix.domain.ports.PixKeyRepository;
import com.bradesco.pix.infrastructure.adapters.rest.mappers.PixKeyMapper;
import com.bradesco.pix.infrastructure.persistence.entities.PixKeyEntity;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaPixKeyRepository implements PixKeyRepository {
    private final SpringPixKeyRepository springRepository;
    private final PixKeyMapper mapper;

    public JpaPixKeyRepository(SpringPixKeyRepository springRepository, PixKeyMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<PixKey> findByKey(String key) {
        return springRepository.findByKeyValue(key)
                .map(mapper::toDomain);
    }

    @Override
    public List<PixKey> findByAccountNumberAndAgency(String accountNumber, String agency) {
        return springRepository.findByAccountNumberAndAgency(accountNumber, agency)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public PixKey save(PixKey pixKey) {
        PixKeyEntity entity = mapper.toEntity(pixKey);
        PixKeyEntity saved = springRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void delete(String key) {
        springRepository.deleteByKeyValue(key);
    }

}