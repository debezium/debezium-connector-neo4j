/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import io.debezium.config.CommonConnectorConfig;
import io.debezium.connector.AbstractSourceInfoStructMaker;

/**
 * Creates the Kafka Connect schema and struct for the Neo4j connector's source info block.
 *
 */
public class Neo4jSourceInfoStructMaker extends AbstractSourceInfoStructMaker<SourceInfo> {

    private Schema schema;

    @Override
    public void init(String connector, String version, CommonConnectorConfig connectorConfig) {
        super.init(connector, version, connectorConfig);
        // TODO: add Neo4j-specific source fields (changeId, txId, seq, captureMode) to the schema
        schema = commonSchemaBuilder()
                .name(connectorConfig.schemaNameAdjuster().adjust("io.debezium.connector.neo4j.Source"))
                .build();
    }

    @Override
    public Schema schema() {
        return schema;
    }

    @Override
    public Struct struct(SourceInfo sourceInfo) {
        // TODO: populate Neo4j-specific source fields
        return commonStruct(sourceInfo);
    }
}
