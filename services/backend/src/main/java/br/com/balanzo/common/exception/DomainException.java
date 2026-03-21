package br.com.balanzo.common.exception;

/**
 * Thrown when a domain rule or invariant is violated.
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
