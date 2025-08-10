package com.example.filestorage.excepton;

public class CreateFileException extends RuntimeException {
    public CreateFileException(String message) {
        super(message);
    }
}
