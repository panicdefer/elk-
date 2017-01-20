package com.hyku.flume.elasticsearch.serializer;

import java.io.IOException;

import org.apache.flume.Context;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializerString implements Serializer {

	private static final Logger logger = LoggerFactory.getLogger(SerializerString.class);

	private String serializerField;

	@Override
	public void initialize(Context context, String serializerField) {
		this.serializerField = serializerField;

	}

	@Override
	public void serializer(XContentBuilder builder, String data) throws IOException {
		logger.info("ooooooooooooooooooooooooooooooooooo");
		logger.info("=== serializerField======"+ serializerField);
		logger.info("===  data ==============="+ data);
		builder.field(serializerField, data);
	}

}
