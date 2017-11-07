package edu.ucu.assigmenttwo.mySpring;

public interface Config {
    <T> Class<T> getImpl(Class<T> ifc);
}
