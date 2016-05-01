package com.ae.caffeserver.service;

import com.ae.caffeserver.classifier.Classifier;
import com.ae.caffeserver.classifier.ClassifierConfig;
import com.ae.caffeserver.classifier.MovieProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by ae on 15-4-16.
 */

@Component
public class CommandLineClassifier implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Classifier classifier;

    @Autowired
    private ClassifierConfig config;

    @Autowired
    private MovieProcessor movieProcessor;

    @PostConstruct
    private void init() {
        try {
            classifier.setUp(config);
            movieProcessor.setUp(config);
        } catch (IOException e) {
            log.error("Could not initialize", e);
        }
    }

    @Override
    public void run(String... strings) throws Exception {
        if (strings.length != 2) {
            throw new IllegalArgumentException("Expected [-m | -i] file");
        }
        if (classifier == null || !classifier.initialized()) {
            throw new IllegalStateException("Classifier was not initialized");
        }
        String type = strings[0];
        if (!("-m".equals(type) || "-i".equals(type))) {
            throw new IllegalArgumentException("Expected [-m | -i] file");
        }
        String file = strings[1];
        if ("-m".equals(type)) {
            movieProcessor.handleMovie(file);
        } else {
            log.info("Classified as: " + classifier.classify(file, 2).get(0).getLeft());
        }
    }
}
