package com.hyku.flume.elasticsearch.serializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.flume.Context;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializerDate implements Serializer {

	private static final Logger logger = LoggerFactory.getLogger(SerializerDate.class);

	private String serializerField;
	private DateFormat format;

	public void initialize(Context context, String serializerField) {
		this.serializerField = serializerField;

	}

	public void serializer(XContentBuilder builder, String data) throws IOException {
		try {
			builder.field(serializerField, format.parse(data));
		} catch (ParseException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	@Override
	public void configure(Context context) {
		String pattern = context.getString("pattern");
		if (pattern == null) {
			format = new SimpleDateFormat();
		}

		format = new SimpleDateFormat(pattern);
	}

}
