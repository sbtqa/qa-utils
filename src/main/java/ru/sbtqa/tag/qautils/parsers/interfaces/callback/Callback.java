package ru.sbtqa.tag.qautils.parsers.interfaces.callback;

/**
 * Simple callback
 *
 * @param <P> Object to inject
 * @param <R> Object to return
 */
public interface Callback<P extends Object, R extends Object> {

    /**
     *
     * @param p Object to inject
     * @return callback result
     */
    public R call(P p);
}
