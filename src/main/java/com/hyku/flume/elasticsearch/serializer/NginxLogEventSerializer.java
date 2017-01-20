package com.hyku.flume.elasticsearch.serializer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.sink.elasticsearch.ContentBuilderUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.elasticsearch.common.io.BytesStream;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * 
 * @author yangjian 收集Nginx日志的序列化类
 *
 */
public class NginxLogEventSerializer implements NginxLogSerializer {

	// 日志
	private static final Logger logger = LoggerFactory.getLogger(NginxLogEventSerializer.class);

	private static ImmutableMap<String, Serializer> serializers = new ImmutableMap.Builder<String, Serializer>()
			.build();

	public static final Serializer DEFAULT_SERIALIZER_OBJECT = new SerializerString();
	ImmutableList<String> fieldList = new ImmutableList.Builder<String>().build();

	public void configure(Context context) {
		String logkey = context.getString("LogKey");
		Preconditions.checkArgument(!StringUtils.isEmpty(logkey), "LogKey is Null");
		String[] fields = logkey.split("\\|");
		Map<String, Serializer> map = new HashMap<String, Serializer>();
		Context serializerContext = new Context(context.getSubProperties("LogKey."));
		for (String field : fields) {
			Context fieldContext = new Context(serializerContext.getSubProperties(field + "."));
			String clazzName = fieldContext.getString("serializer", "string");
			try {
				map.put(field, newInstance(field, clazzName, fieldContext));
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage());
				throw new RuntimeException(e);
			} catch (InstantiationException e) {
				logger.error(e.getMessage());
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
				throw new RuntimeException(e);
			}
		}
		fieldList = ImmutableList.copyOf(fields);
		serializers = ImmutableMap.copyOf(map);
	}

	private Serializer newInstance(String field, String clazzName, Context context)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		Class<? extends Serializer> clazz = null;
		try {
			clazz = SerializerDataType.valueOf(clazzName.toUpperCase()).getClz();
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		Serializer serializer = clazz.newInstance();
		serializer.initialize(context, field);
		return serializer;
	}

	public BytesStream getContentBuilder(Event event) throws IOException {
		XContentBuilder builder = XContentFactory.jsonBuilder();
		parseBody(builder, event);
		parseHeaders(builder, event);
		return builder;
	}

	private void parseBody(XContentBuilder builder, Event event) throws IOException, UnsupportedEncodingException {
		String body = new String(event.getBody(), charset);
		Map<String, String> bodyMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		bodyMap = mapper.readValue(body, new TypeReference<HashMap<String, String>>() {
		});
		
		for (String key : bodyMap.keySet()) {
			serializerData(builder, key, bodyMap.get(key).toString());
		}
	}

	private void serializerData(XContentBuilder builder, String key, String data)
			throws IOException, UnsupportedEncodingException {
		Serializer serializer = getSerializer(key);
		serializer.serializer(builder, data);
	}

	private Serializer getSerializer(String key) {
		Serializer result = serializers.get(key);
		if (result != null) {
			return result;
		}
		return DEFAULT_SERIALIZER_OBJECT;
	}

	private void parseHeaders(XContentBuilder builder, Event event) throws IOException {
		Map<String, String> headers = event.getHeaders();
		for (String key : headers.keySet()) {
			ContentBuilderUtil.appendField(builder, key, headers.get(key).getBytes(charset));
		}
		builder.endObject();
	}

}
