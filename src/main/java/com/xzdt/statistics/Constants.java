package com.xzdt.statistics;

public interface Constants {

    // kafka 相关变量
    String KAFKA_BROKER_LIST = "kafka.broker.list";
    String KAFKA_TOPIC_NAME = "kafka.topic.name";
    String KAFKA_ACKS = "kafka.acks";
    String KAFKA_RETRIES = "kafka.retries";
    String KAFKA_BATCH_SIZE = "kafka.batch.size";
    String KAFKA_LINGER_MS = "kafka.linger.ms";
    String KAFKA_BUFFER_MEMORY = "kafka.buffer.memory";
    String KAFKA_KAY_SERIALIZER = "kafka.key.serializer";
    String KAFKA_VALUE_SERIALIZER = "kafka.key.serializer";
    String KAFKA_GROUP_ID = "kafka.group.id";
    String KAFKA_ENABLE_AUTO_COMMIT = "kafka.enable.auto.commit";
    String KAFKA_AUTO_OFFSET_RESET = "kafka.auto.offset.reset";
    String KAFKA_MAX_POLL_RECORDS = "kafka.max.poll.records";
    String KAFKA_MAX_POLL_INTERVAL_MS = "kafka.max.poll.interval.ms";
    String KAFKA_TOPIC_NAME_OF_USERS = "kafka.topic.name.of.users";
    String KAFKA_GROUP_ID_OF_USERS = "kafka.group.id.of.users";
    String KAFKA_KAY_DESERIALIZER = "kafka.key.deserializer";
    String KAFKA_VALUE_DESERIALIZER = "kafka.value.deserializer";

    // 相关文件路径
    String FILE_PATH = "file.path";

    // 文本分割符
    String REGEX = "regex";
    // 字段名
    String HEAD_NAME = "head.name";

    // jdbc相关变量
    String JDBC_URL = "jdbc.url";
    String JDBC_DRIVER = "jdbc.driver";
    String JDBC_USERNAME = "jdbc.username";
    String JDBC_PASSWORD = "jdbc.password";
    String JDBC_TABLE = "jdbc.table";
    String JDBC_CONN_SIZE = "jdbc.conn.size";
    String JDBC_TABLE_OF_USERS = "jdbc.table.of.users";


    // hadoop相关变量
    String HADOOP_CORE_SITE = "hadoop.core.site";
    String HADOOP_HDFS_SITE = "hadoop.hdfs.site";

    // HBase相关变量
    String HBASE_SITE = "hbase.site";
    String HBASE_TABLE_OF_USERS = "hbase.table.of.users";
}
