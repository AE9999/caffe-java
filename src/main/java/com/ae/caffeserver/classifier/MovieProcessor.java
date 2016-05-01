package com.ae.caffeserver.classifier;

import com.ae.caffeserver.util.Utils;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

/**
 * Created by ae on 22-4-16.
 */
@Component
public class MovieProcessor {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Classifier classifier;

    @Autowired
    private Utils utils;

    public void setUp(ClassifierConfig config) throws IOException {
        classifier.setUp(config);
    }

    private void doHandleMovie(File file) {
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(file);
        OpenCVFrameConverter.ToMat openCVFrameConverter = new OpenCVFrameConverter.ToMat();
        try {
            File myDir = utils.createDir();
            File cleanDir = utils.createDirInDir(myDir, "model");
            File ncleanDir = utils.createDirInDir(myDir, "not_model");

            frameGrabber.start();
            int lenght = frameGrabber.getLengthInFrames();
            for(int i = 0; i < lenght; i += 25) {
                log.info(String.format("Processing frame %d out of %d ..", i, lenght));
                frameGrabber.setFrameNumber(i);
                Frame frame = frameGrabber.grabFrame();
                opencv_core.Mat frameImg = openCVFrameConverter.convertToMat(frame);
                String fname = "file " + i + ".jpg";
                File toWrite = utils.createFileInDir(myDir, fname);
                imwrite(toWrite.getAbsolutePath(), frameImg);
                List<Prediction> predictions = classifier.classify(toWrite, 1);
                String m = String.format("%s is classified as %s ..",
                        toWrite.getAbsolutePath(),
                        predictions.get(0).getLeft());
                if ("NOT_GIRL_MODEL".equals(predictions.get(0).getLeft())) {
                    File toWrite_ = utils.createFileInDir(cleanDir, fname);
                    imwrite(toWrite_.getAbsolutePath(), frameImg);
                } else {
                    File toWrite_ = utils.createFileInDir(ncleanDir, fname);
                    imwrite(toWrite_.getAbsolutePath(), frameImg);
                }
                log.info(m);
            }
            frameGrabber.stop();
        } catch (FrameGrabber.Exception | IOException e) {
            log.error("Could not handle movie", e);
        }
    }

    public void handleMovie(InputStream is) throws IOException {
        doHandleMovie(utils.inputsStream2File(is, true));
    }

    public void handleMovie(String fname) throws IOException {
        doHandleMovie(new File(fname));
    }


}
