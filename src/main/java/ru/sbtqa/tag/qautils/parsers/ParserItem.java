package ru.sbtqa.tag.qautils.parsers;

/**
 * Item to parse.
 * Consist of string source to be parsed and path to be applied on source.
 */
public class ParserItem {

    private final String source;
    private final String path;

    /**
     * Create parse item object
     *
     * @param source a string source
     * @param path the path to apply
     */
    public ParserItem(String source, String path) {
        this.source = source;
        this.path = path;
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @return source
     */
    public String getSource() {
        return source;
    }
}
