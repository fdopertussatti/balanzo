package br.com.balanzo.common.exception;

/**
 * Thrown when the user lacks permission to perform the operation.
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
