package ru.sbtqa.tag.qautils.properties;

public class PropsRuntimeException extends RuntimeException {
    
    /**
     * 
     * @param e a {@link java.lang.Throwable} object.
     */
    public PropsRuntimeException(Throwable e) {
        super(e);
    }
    
    /**
     *
     * @param message a {@link java.lang.String} object.
     * @param e a {@link java.lang.Throwable} object.
     */
    public PropsRuntimeException(String message, Throwable e) {
        super(message, e);
    }

    /**
     *
     * @param message a {@link java.lang.String} object.
     */
    public PropsRuntimeException(String message) {
        super(message);
    }
}
