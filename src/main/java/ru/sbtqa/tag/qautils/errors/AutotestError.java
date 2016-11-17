package ru.sbtqa.tag.qautils.errors;

/**
 * Error for test exceptions
 *
 * @author Viktor Sidochenko <viktor.sidochenko@gmail.com>
 */
public class AutotestError extends AssertionError {

    public AutotestError(String message) {
        super(message);
    }

    public AutotestError(String message, Throwable cause) {
        super(message, cause);
    }

    public AutotestError(Throwable cause) {
        super(cause);
    }

}
