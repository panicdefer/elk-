package com.hyku.flume.elasticsearch.serializer;

/**
 * 需要序列化的枚举类型
 * 
 * @author yangjian
 *
 */
public enum SerializerDataType {
	STRING(com.hyku.flume.elasticsearch.serializer.SerializerString.class), INT(
			com.hyku.flume.elasticsearch.serializer.SerializerInt.class), DATE(
					com.hyku.flume.elasticsearch.serializer.SerializerDate.class);

	private Class<? extends Serializer> clz;

	private SerializerDataType(Class<? extends Serializer> clz) {
		this.clz = clz;
	}

	public Class<? extends Serializer> getClz() {
		return this.clz;
	}

}
