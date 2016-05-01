# Description
This is an image classification tool which recognizes models in images. (Yes, it is that cheesy.)

# Building
Building with docker (results in 7gb image):
  - Install & Run docker.
  - Install maven.
  - Run mvn install in main directory.

Building without docker:
  - Install maven.
  - Install dependencies as suggested in Dockerfile
  - Run `mvn install -DskipDockerBuild` in main directory.

# Running with docker
Running with docker:
  - Start docker
  - Start container with `sudo docker run -it -v ${someDirectoryOfImages}:/images ae/caffe-java /bin/bash`

# Running in general
Use `java -jar caffejava.jar [flag] [file]`. where flag is either '-i' or '-m', with '-i; indicating you are classifying a single image, of '-m' indicating you are want to process a movie file. If you classify an image file the output will be writen directly to the screen. If you classify a movie the file will be split into different frames which will be classified and writen to a tempdir in the  /tmp (or in the windows equivalent) directory.