package com.bradesco.pix.infrastructure.adapters.rest.controllers;

import com.bradesco.pix.application.usecases.CreatePixKeyUseCase;
import com.bradesco.pix.application.usecases.ExecutePixTransferUseCase;
import com.bradesco.pix.domain.entities.PixKey;
import com.bradesco.pix.domain.entities.PixTransaction;
import com.bradesco.pix.infrastructure.adapters.rest.controllers.requests.CreatePixKeyRequest;
import com.bradesco.pix.infrastructure.adapters.rest.controllers.requests.PixTransferRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pix")
@CrossOrigin(origins = "*")
public class PixController {
    private final CreatePixKeyUseCase createPixKeyUseCase;
    private final ExecutePixTransferUseCase executePixTransferUseCase;

    public PixController(CreatePixKeyUseCase createPixKeyUseCase,
                         ExecutePixTransferUseCase executePixTransferUseCase) {
        this.createPixKeyUseCase = createPixKeyUseCase;
        this.executePixTransferUseCase = executePixTransferUseCase;
    }

    @PostMapping("/keys")
    public ResponseEntity<PixKey> createPixKey(@Valid @RequestBody CreatePixKeyRequest request) {
        PixKey pixKey = createPixKeyUseCase.execute(
                request.getKey(),
                request.getType(),
                request.getAccountNumber(),
                request.getAgency()
        );
        return ResponseEntity.ok(pixKey);
    }

    @PostMapping("/transfer")
    public ResponseEntity<PixTransaction> executeTransfer(@Valid @RequestBody PixTransferRequest request) {
        PixTransaction transaction = executePixTransferUseCase.execute(
                request.getSourceAccountNumber(),
                request.getSourceAgency(),
                request.getDestinationPixKey(),
                request.getAmount(),
                request.getDescription()
        );
        return ResponseEntity.ok(transaction);
    }
}