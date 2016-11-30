package ru.sbtqa.tag.qautils.errors;

/**
 * Thrown to indicate that an autotest has failed.
 */
public class AutotestError extends AssertionError {

    /**
     * Constructs a new {@code AutotestError} with the specified detail message.
     * 
     * @param message the detail message, may be {@code null}
     */
    public AutotestError(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code AutotestError} with the specified cause.
     * 
     * @param cause the cause, may be {@code null}
     */
    public AutotestError(Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructs a new {@code AutotestError} with the specified
     * detail message and cause.
     * 
     * @param message the detail message, may be {@code null}
     * @param cause the cause, may be {@code null}
     */
    public AutotestError(String message, Throwable cause) {
        super(message, cause);
    }
}
