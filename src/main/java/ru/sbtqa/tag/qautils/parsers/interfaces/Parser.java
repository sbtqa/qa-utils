package ru.sbtqa.tag.qautils.parsers.interfaces;

import ru.sbtqa.tag.qautils.parsers.exception.ParserException;

public interface Parser {

    /**
     * Applies this path to provided source.
     *
     * @param <T> expected return type
     * @param source a string source
     * @param path the path to apply
     * @return an object matched by the given path
     * @throws ParserException if an error occurred while applying the path on source
     */
    public <T extends Object> T read(String source, String path) throws ParserException;

}
