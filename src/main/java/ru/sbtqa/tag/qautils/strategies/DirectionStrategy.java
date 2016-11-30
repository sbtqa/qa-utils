package ru.sbtqa.tag.qautils.strategies;

public enum DirectionStrategy {

    /**
     * take elements with any coordinates intersection in the range of by-element
     */
    FREE,
    
    /**
     * take elements with central coordinate intersection in the range of by-element
     */
    STRICT
}
