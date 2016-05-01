package com.ae.caffeserver.classifier;

import org.apache.commons.lang3.tuple.MutablePair;

/**
 * Created by ae on 16-4-16.
 */
public class Prediction extends MutablePair<String, Float> {
    public Prediction(String left, Float right) {
        setLeft(left);
        setRight(right);
    }



}
