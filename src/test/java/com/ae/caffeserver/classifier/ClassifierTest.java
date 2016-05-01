package com.ae.caffeserver.classifier;


import com.ae.caffeserver.util.Utils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ae on 17-4-16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class ClassifierTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Classifier classifier;

    @Autowired
    private MovieProcessor movieProcessor;

    @Autowired
    private ClassifierConfig config;

    @Autowired
    private Utils utils;

    @Before
    public void setup() throws IOException {
        classifier.setUp(config);
        movieProcessor.setUp(config);
    }

    @Test
    public void testModelClassifierEasy() throws IOException {
        List<Prediction> predictions;
        predictions = classifier.classify(utils.getStream("/model.jpg"), 1);
        assertThat(predictions).hasSize(1);
        assertThat(predictions.get(0).getLeft().equals("GIRL_MODEL")).isTrue();
    }

    @Test
    public void testCleanClassifierEasy() throws IOException {
        List<Prediction> predictions;
        predictions = classifier.classify(utils.getStream("/clean.jpg"), 1);
        assertThat(predictions).hasSize(1);
        assertThat(predictions.get(0).getLeft().equals("NOT_GIRL_MODEL")).isTrue();
    }

    @Test
    public void testCleanClassifierHard() throws IOException {
        List<Prediction> predictions;
        predictions = classifier.classify(utils.getStream("/borderline.jpg"), 1);
        assertThat(predictions).hasSize(1);
        assertThat(predictions.get(0).getLeft().equals("NOT_GIRL_MODEL")).isTrue();
    }

}
