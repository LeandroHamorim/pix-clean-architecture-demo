package com.bradesco.pix.domain.ports;

import com.bradesco.pix.domain.entities.PixTransaction;

public interface BacenService {
    void notifyTransaction(PixTransaction transaction);
}
