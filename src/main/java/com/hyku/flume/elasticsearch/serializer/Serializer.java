package com.hyku.flume.elasticsearch.serializer;

import java.io.IOException;

import org.apache.flume.Context;
import org.apache.flume.conf.Configurable;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * 序列化接口
 * 
 * @author yangjian
 *
 **/
public interface Serializer extends Configurable {
	public void initialize(Context context, String serializerField);

	public void serializer(XContentBuilder builder, String data) throws IOException;

	default void configure(Context context) {
		System.out.println("read information");
	}

}
