package com.ae.caffeserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ae on 22-4-16.
 */

@Component
public class Utils {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public InputStream getStream(String name) throws IOException {
        return Utils.class.getResourceAsStream(name);
    }

    public String inputsStream2Filename(InputStream is, boolean tmp) throws IOException {
        File tmpFile = File.createTempFile("model", "");
        org.apache.commons.io.FileUtils.copyInputStreamToFile(is, tmpFile);
        log.info(String.format("Created file %s for is", tmpFile.getAbsolutePath()));
        if(tmp) { tmpFile.deleteOnExit(); }
        return tmpFile.getAbsolutePath();
    }

    public File inputsStream2File(InputStream is, boolean tmp) throws IOException {
        File tmpFile = File.createTempFile("model", "");
        org.apache.commons.io.FileUtils.copyInputStreamToFile(is, tmpFile);
        log.info(String.format("Created file %s for is", tmpFile.getAbsolutePath()));
        if(tmp) { tmpFile.deleteOnExit(); }
        return tmpFile;
    }

    public void testTrue(boolean b, String message) {
        if (!b) {
            throw new IllegalStateException(message);
        }
    }

    public File createDir() throws IOException {
        File file = File.createTempFile("tmpDir", "tmpDir");
        file.delete();
        file.mkdir();
        return file;
    }

    public File createFileInDir(File myDir, String fname) throws IOException {
        testTrue(myDir.isDirectory(), "Can only create file in directory");
        testTrue(myDir.canWrite(), "Must be able to write in directory");
        String fname_ = myDir.getAbsolutePath() + File.separator + fname;
        File file = new File(fname_);
        testTrue(!file.exists(), "Must be able to write in directory");
        boolean ok =  file.createNewFile();
        testTrue(ok, "File must be created");
        testTrue(file.canWrite(), "Must be able to write to file");
        log.info(String.format("Created %s ..", file.getAbsolutePath()));
        return file;
    }

    public File createDirInDir(File myDir, String dname) throws IOException {
        testTrue(myDir.isDirectory(), "Can only create file in directory");
        testTrue(myDir.canWrite(), "Must be able to write in directory");
        String fname_ = myDir.getAbsolutePath() + File.separator + dname;
        File file = new File(fname_);
        testTrue(!file.exists(), "Must be able to write in directory");
        boolean ok = file.mkdir();
        testTrue(ok, "Dir must be created");
        testTrue(file.canWrite(), "Must be able to write to file");
        log.info(String.format("Created %s ..", file.getAbsolutePath()));
        return file;
    }
}
