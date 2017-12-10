package edu.ucu.assignmentthree.validator;


import org.apache.spark.api.java.function.Function;


public interface Validator<T> {
    Function<T, Boolean> createValidator();
}
