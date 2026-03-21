package br.com.balanzo.common.exception;

/**
 * Thrown when an operation requires authentication but none was provided.
 */
public class AuthRequiredException extends RuntimeException {

    public AuthRequiredException() {
        super("Authentication required");
    }
}
