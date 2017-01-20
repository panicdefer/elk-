package com.hyku.flume.aws.s3.sink;

import org.apache.flume.Context;
import org.apache.flume.sink.hdfs.HDFSEventSink;

public class Flume2S3EventSink extends HDFSEventSink {
	
	@Override
	public void configure(Context context) {
		String inUseSuffixField = "hdfs.inUseSuffix";
		if (!context.containsKey(inUseSuffixField))
			context.put(inUseSuffixField, "");
		super.configure(context);
	}

}
