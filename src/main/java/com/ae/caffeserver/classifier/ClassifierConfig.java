package com.ae.caffeserver.classifier;

import com.ae.caffeserver.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ae on 22-4-16.
 */
@Component
public class ClassifierConfig {

    @Autowired
    private Utils utils;

    //@Value("${modelName}")
    private String modelName = "/model/deploy.prototxt";

    //@Value("${trainedModel}")
    private String trainingName = "/model/iter_10000.caffemodel";

    //@Value("${meanFile}")
    private String meanName = "/model/imagenet_mean.binaryproto";

    //@Value("${labelFile}")
    private String labelName = "/model/synsets.txt";


    public InputStream getModelFile() throws IOException {
        return utils.getStream(modelName);
    }


    public InputStream getTrainedFile() throws IOException {
        return utils.getStream(trainingName);
    }

    public InputStream getMeanFile() throws IOException {
        return utils.getStream(meanName);
    }


    public InputStream getLabelFile() throws IOException {
        return utils.getStream(labelName);
    }


}
