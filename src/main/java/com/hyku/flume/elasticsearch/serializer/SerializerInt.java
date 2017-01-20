package com.hyku.flume.elasticsearch.serializer;

import java.io.IOException;

import org.apache.flume.Context;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializerInt implements Serializer {

	private static final Logger logger = LoggerFactory.getLogger(SerializerInt.class);

	private String serializerField;

	public void initialize(Context context, String serializerField) {

		this.serializerField = serializerField;
	}

	public void serializer(XContentBuilder builder, String data) throws IOException {
		builder.field(serializerField, Integer.parseInt(data));

	}

}
