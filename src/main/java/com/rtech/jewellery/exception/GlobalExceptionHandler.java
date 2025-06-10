package com.rtech.jewellery.exception;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FirebaseAuthException.class)
    public ResponseEntity<Map<String, String>> handleFirebaseAuthException(FirebaseAuthException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Invalid or expired token.");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}

