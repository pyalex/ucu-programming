package edu.ucu.assignmenttwo.mySpring;

public interface Config {
    <T> Class<T> getImpl(Class<T> ifc);
}
