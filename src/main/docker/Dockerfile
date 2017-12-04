FROM ubuntu:14.04
RUN  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true \
     | debconf-set-selections  \
     && echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" \
     | tee /etc/apt/sources.list.d/webupd8team-java.list \
     &&  echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" \
     | tee -a /etc/apt/sources.list.d/webupd8team-java.list \
     && apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886  && \
     apt-get update && \
     apt-get install -y git \
                       maven \
                       libusb-dev \
                       libusb-1.0-0-dev \
                       build-essential \
                       curl \
                       nasm \
                       cmake \
                       yasm \
                       gfortran \
                       libx11-dev \
                       pkg-config \
                       libx264-dev \
                       libxext-dev \
                       libxfixes-dev \
                       zlib1g-dev \
                       oracle-java8-installer \
                       oracle-java8-unlimited-jce-policy  && \
      git clone https://github.com/bytedeco/javacpp.git && \
      git clone https://github.com/bytedeco/javacpp-presets && \
      git clone https://github.com/bytedeco/javacv.git
COPY java_cpp_cafe.patch /
COPY javacv.patch /
RUN (cd javacpp-presets && git checkout 1.2 && patch -p1 < /java_cpp_cafe.patch) && \
    (cd javacv && git checkout 1.2 && patch -p1 < /javacv.patch)
RUN (cd javacpp && mvn install)
RUN (cd javacpp-presets && ./cppbuild.sh install opencv \
                                                     caffe \
                                                 libfreenect \
                                                 videoinput \
                                                 artoolkitplus \
                                                 flandmark \
                                                 libdc1394 \
                                                 ffmpeg )
RUN (cd javacpp-presets && \
     mvn clean install -Djavacpp.platform=linux-x86_64 \
                       -Djavacpp.platform.dependency=false \
                       --projects .,opencv,caffe,libfreenect,videoinput,artoolkitplus,flandmark,libdc1394,ffmpeg)
RUN (cd javacv && mvn install && mvn install -Pffmpeg)
COPY caffejava.jar /
RUN apt-get install -y  libgtk2.0