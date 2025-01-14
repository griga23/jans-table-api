package io.confluent.flink;

import org.apache.flink.table.api.*;

import static org.apache.flink.table.api.Expressions.*;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.TableDescriptor;

public class JansTableAPITest {

    // define input and output table for transformation
    public static final String SOURCE_TABLE = "transactions";

    public static void main(String[] args) {

        TableEnvironment env = TableEnvironment.create(EnvironmentSettings.inStreamingMode());

        env.createTable(SOURCE_TABLE, CLOUD_AVRO_TRANSACTIONS_DESCRIPTOR);
        Table transactionsTable = env.from(SOURCE_TABLE).select(withAllColumns());

        transactionsTable.printSchema();

        //env.executeSql("SELECT * FROM transactions").print();
        transactionsTable.execute().print();

    }


    public static TableDescriptor CLOUD_AVRO_TRANSACTIONS_DESCRIPTOR =
            TableDescriptor.forConnector("kafka")
                    .schema(
                            Schema.newBuilder()
                                    .column("id", DataTypes.STRING())
                                    .column("user_id", DataTypes.BIGINT())
                                    .column("amount", DataTypes.BIGINT())
                                    .column("product_id", DataTypes.BIGINT())
                                    .column("time", DataTypes.STRING())
                                    .column("transaction_time", DataTypes.STRING())
                                    .build())
                    .format("avro-confluent")
                    .option("properties.group.id", "myGroup")
                    .option("scan.startup.mode", "earliest-offset")
                    .option("properties.bootstrap.servers", "pkc-7xoy1.eu-central-1.aws.confluent.cloud:9092")
                    .option("properties.security.protocol", "SASL_SSL")
                    .option("properties.sasl.mechanism", "PLAIN")
                    .option("properties.sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule   required username=\"JKPRHZ2XYU6PYK6W\"   password=\"HNfsck/uGONOvHRxcDlxmAgU5JK2XWEqt1EjHWk2d9k28o6QkdQvSAvcc0ZmNryj\";")
                    .option("topic", SOURCE_TABLE)
                    .option("avro-confluent.url", "https://psrc-4xrp1.eu-central-1.aws.confluent.cloud")
                    .option("avro-confluent.basic-auth.credentials-source","USER_INFO")
                    .option("avro-confluent.basic-auth.user-info","RY7EWBA7FMXI2QCI:8rHsBFVoo7AUb2bW2xDEqoIX22ae2Q/5xJ56fRWgjca0+hUY2A0oaiLOfRbXQRmV")
                    .build();


}
