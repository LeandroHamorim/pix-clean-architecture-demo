package com.bradesco.pix.infrastructure.adapters.rest.controllers;

import com.bradesco.pix.application.usecases.CreatePixKeyUseCase;
import com.bradesco.pix.application.usecases.ExecutePixTransferUseCase;
import com.bradesco.pix.domain.entities.PixKey;
import com.bradesco.pix.domain.entities.PixTransaction;
import com.bradesco.pix.infrastructure.adapters.rest.controllers.requests.CreatePixKeyRequest;
import com.bradesco.pix.infrastructure.adapters.rest.controllers.requests.PixTransferRequest;
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
    public ResponseEntity<PixKey> createPixKey(@RequestBody CreatePixKeyRequest request) {
        try {
            PixKey pixKey = createPixKeyUseCase.execute(
                    request.getKey(),
                    request.getType(),
                    request.getAccountNumber(),
                    request.getAgency()
            );
            return ResponseEntity.ok(pixKey);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<PixTransaction> executeTransfer(@RequestBody PixTransferRequest request) {
        try {
            PixTransaction transaction = executePixTransferUseCase.execute(
                    request.getSourceAccountNumber(),
                    request.getSourceAgency(),
                    request.getDestinationPixKey(),
                    request.getAmount(),
                    request.getDescription()
            );
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}