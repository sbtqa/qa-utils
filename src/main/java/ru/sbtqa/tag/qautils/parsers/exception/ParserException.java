package ru.sbtqa.tag.qautils.parsers.exception;

/**
 *
 * @author Konstantin Maltsev <mkypers@gmail.com>
 */
public class ParserException extends Exception {

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String string, Throwable ex) {
        super(string, ex);
    }
}
