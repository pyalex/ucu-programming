package edu.ucu.assigmenttwo.mySpring;

public interface ObjectConfigurator {
    <T> void configure(Class<T> type, Object o);
}
