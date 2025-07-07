package dev.dgomes.footballNews.domain;

public class ApiException extends RuntimeException {
    final int statusCode;

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}