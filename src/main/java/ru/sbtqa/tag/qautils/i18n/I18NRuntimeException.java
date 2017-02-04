package ru.sbtqa.tag.qautils.i18n;

public class I18NRuntimeException extends RuntimeException {

    /**
     *
     * @param e a {@link Throwable} object.
     */
    public I18NRuntimeException(Throwable e) {
        super(e);
    }

    /**
     *
     * @param message a {@link String} object.
     * @param e a {@link Throwable} object.
     */
    public I18NRuntimeException(String message, Throwable e) {
        super(message, e);
    }

    /**
     *
     * @param message a {@link String} object.
     */
    public I18NRuntimeException(String message) {
        super(message);
    }
}
