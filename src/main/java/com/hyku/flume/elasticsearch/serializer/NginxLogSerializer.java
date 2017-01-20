package com.hyku.flume.elasticsearch.serializer;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.flume.Event;
import org.apache.flume.conf.ComponentConfiguration;
import org.apache.flume.sink.elasticsearch.ElasticSearchEventSerializer;
import org.elasticsearch.common.io.BytesStream;

public interface NginxLogSerializer extends ElasticSearchEventSerializer {
	public static final Charset charset = Charset.defaultCharset();

	abstract BytesStream getContentBuilder(Event event) throws IOException;
	
	@Override
	default void configure(ComponentConfiguration conf) {
		
	}
}
