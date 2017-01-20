FROM hyku/java:1.0

MAINTAINER cherry <jian.yang@hyku.com>

ENV ELASTICSEARCH_VERSION=5.1.1
ENV ELASTICSEARCH_URL_BASE=https://artifacts.elastic.co/downloads/elasticsearch
ENV ELASTICSEARCH_HOME=/usr/local/elasticsearch

RUN set -ex \
        && apk update \
        && apk add ca-certificates wget \
        && apk update ca-certificates \
        && mkdir -p $ELASTICSEARCH_HOME \
	&& wget --no-check-certificate  -qO- $ELASTICSEARCH_URL_BASE/elasticsearch-$ELASTICSEARCH_VERSION.tar.gz | tar -zxvf - -C $ELASTICSEARCH_HOME --strip 1

RUN set -ex \
    && addgroup elasticsearch \
    && adduser -D -G elasticsearch -h $ELASTICSEARCH_HOME elasticsearch \
    && chown -R elasticsearch:elasticsearch $ELASTICSEARCH_HOME
    
RUN chown -R elasticsearch:elasticsearch $ELASTICSEARCH_HOME

USER elasticsearch
RUN  mkdir -p $ELASTICSEARCH_HOME/{data,logs}
RUN  mkdir -p $ELASTICSEARCH_HOME/data/nodes
Add elasticsearch.yml $ELASTICSEARCH/config/

VOLUME $ELASTICSEARCH_HOME/data

EXPOSE 9200 

EXPOSE 9300

WORKDIR $ELASTICSEARCH_HOME
CMD ["bin/elasticsearch","-E","network.host=0.0.0.0"]

