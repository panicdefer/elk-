FROM hyku/java:1.0

MAINTAINER cherrylouis <jian.yang@hyku.com>

ENV FLUME_HOME=/usr/local/flume
ENV AGENT=agent
ENV CONFIG=flume.conf
ENV LOGGER=INFO,console

RUN mkdir -p $FLUME_HOME
RUN mkdir -p $FLUME_HOME/plugins.d
WORKDIR $FLUME_HOME
RUN wget -qO- http://apache.claz.org/flume/1.7.0/apache-flume-1.7.0-bin.tar.gz | tar zxvf - -C $FLUME_HOME --strip 1
RUN rm -f  $FLUME_HOME/lib/flume-ng-elasticsearch-sink-1.7.0.jar
RUN rm -f  $FLUME_HOME/lib/jackson-*
ADD flume.conf $FLUME_HOME/conf/flume.conf
COPY lib/* $FLUME_HOME/lib/
COPY log4j2.properties $FLUME_HOME/conf/
COPY access.log /var/log/nginx/access.log
RUN apk update
RUN apk add vim

VOLUME $FLUME_HOME/conf
#VOLUME /var/log/nginx/access.log
VOLUME $FLUME_HOME/plugins.d

CMD bin/flume-ng agent --conf $FLUME_HOME/conf -f $FLUME_HOME/conf/$CONFIG -n $AGENT -Dflume.root.logger=$LOGGER
