package ru.sbtqa.tag.qautils.parsers.interfaces.callback;

import javafx.util.Callback;
import ru.sbtqa.tag.qautils.parsers.ParserItem;

/**
 *
 * @author sbt-maltcev-kyu
 */
public interface ParserCallback extends Callback<ParserItem, Object> {

    /**
     * Callback implementation
     *
     * @param item a {@link ru.sbt.qa.utils.parsers.ParserItem} object
     * @return an object received after parser item applied
     */
    @Override
    public String call(ParserItem item);

}
