package com.bradesco.pix.infrastructure.gateways;

import com.bradesco.pix.domain.entities.PixTransaction;
import com.bradesco.pix.domain.ports.BacenService;
import org.springframework.stereotype.Service;

@Service
public class BacenServiceImpl implements BacenService {

    @Override
    public void notifyTransaction(PixTransaction transaction) {
        // Em um cenário real, aqui seria feita a chamada para a API do BACEN
        // usando RestTemplate, WebClient, etc.
        // apenas para caso transação maior que 5 mil
        System.out.println("Notifying BACEN about transaction: " + transaction.getTransactionId());
        System.out.println("Amount: " + transaction.getAmount());
        System.out.println("From: " + transaction.getSourceAccountNumber());
        System.out.println("To: " + transaction.getDestinationPixKey());

    }
}
