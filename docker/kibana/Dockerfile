FROM hyku/java:1.0
MAINTAINER cherrylouis <jian.yang@hyku.com>

ENV KIBANA_VERSION=5.1.1
ENV KIBANA_URL_BASE=https://artifacts.elastic.co/downloads/kibana
ENV KIBANA_HOME=/usr/local/kibana

RUN apk update \
    && apk add ca-certificates wget \
    && apk update ca-certificates \
    && mkdir -p $KIBANA_HOME \
    && wget --no-check-certificate -qO- $KIBANA_URL_BASE/kibana-$KIBANA_VERSION-linux-x86_64.tar.gz \ 
    | tar -zxvf - -C $KIBANA_HOME --strip 1
Add kibana.yml $KIBANA_HOME/config/

EXPOSE 5601
WORKDIR $KIBANA_HOME
CMD ["bin/kibana"]
