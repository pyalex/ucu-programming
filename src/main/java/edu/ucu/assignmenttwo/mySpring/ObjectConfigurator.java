package edu.ucu.assignmenttwo.mySpring;

public interface ObjectConfigurator {
    <T> void configure(Class<T> type, T o);
}
