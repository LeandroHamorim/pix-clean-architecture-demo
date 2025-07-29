package com.bradesco.pix.infrastructure.adapters.rest.controllers;

import com.bradesco.pix.domain.exceptions.*;
import com.bradesco.pix.infrastructure.adapters.rest.controllers.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(
            EntityNotFoundException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                "ENTITY_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(
            InsufficientBalanceException ex, HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        details.put("currentBalance", ex.getCurrentBalance());
        details.put("requestedAmount", ex.getRequestedAmount());

        ErrorResponse error = new ErrorResponse(
                "INSUFFICIENT_BALANCE",
                ex.getMessage(),
                request.getRequestURI(),
                details
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(InvalidPixKeyException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPixKey(
            InvalidPixKeyException ex, HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        details.put("key", ex.getKey());
        details.put("type", ex.getType());

        ErrorResponse error = new ErrorResponse(
                "INVALID_PIX_KEY",
                ex.getMessage(),
                request.getRequestURI(),
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PixKeyLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handlePixKeyLimitExceeded(
            PixKeyLimitExceededException ex, HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        details.put("currentCount", ex.getCurrentCount());
        details.put("maxAllowed", ex.getMaxAllowed());

        ErrorResponse error = new ErrorResponse(
                "PIX_KEY_LIMIT_EXCEEDED",
                ex.getMessage(),
                request.getRequestURI(),
                details
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(InactiveAccountException.class)
    public ResponseEntity<ErrorResponse> handleInactiveAccount(
            InactiveAccountException ex, HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        details.put("accountNumber", ex.getAccountNumber());
        details.put("agency", ex.getAgency());

        ErrorResponse error = new ErrorResponse(
                "INACTIVE_ACCOUNT",
                ex.getMessage(),
                request.getRequestURI(),
                details
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRule(
            BusinessRuleException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                "BUSINESS_RULE_VIOLATION",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(TransactionProcessingException.class)
    public ResponseEntity<ErrorResponse> handleTransactionProcessing(
            TransactionProcessingException ex, HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        if (ex.getTransactionId() != null) {
            details.put("transactionId", ex.getTransactionId());
        }

        ErrorResponse error = new ErrorResponse(
                "TRANSACTION_PROCESSING_ERROR",
                ex.getMessage(),
                request.getRequestURI(),
                details
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        details.put("fieldErrors", fieldErrors);

        ErrorResponse error = new ErrorResponse(
                "VALIDATION_ERROR",
                "Validation failed for one or more fields",
                request.getRequestURI(),
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                "INVALID_ARGUMENT",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred",
                request.getRequestURI()
        );

        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}