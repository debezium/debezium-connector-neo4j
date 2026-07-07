/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j.converters;

import java.util.Set;

import io.debezium.connector.neo4j.SourceInfo;
import io.debezium.converters.recordandmetadata.RecordAndMetadata;
import io.debezium.converters.spi.CloudEventsMaker;
import io.debezium.converters.spi.SerializerType;
import io.debezium.data.Envelope;
import io.debezium.util.Collect;

/**
 * CloudEvents maker for records produced by the Neo4j connector.
 *
 */
public class Neo4jCloudEventsMaker extends CloudEventsMaker {

    static final Set<String> NEO4J_SOURCE_FIELDS = Collect.unmodifiableSet(
            SourceInfo.CHANGE_ID_KEY,
            SourceInfo.TX_ID_KEY,
            SourceInfo.SEQ_KEY,
            SourceInfo.CAPTURE_MODE_KEY);

    public Neo4jCloudEventsMaker(RecordAndMetadata recordAndMetadata,
                                 SerializerType dataContentType,
                                 String dataSchemaUriBase,
                                 String cloudEventsSchemaName) {
        super(recordAndMetadata, dataContentType, dataSchemaUriBase, cloudEventsSchemaName,
                Envelope.FieldName.BEFORE, Envelope.FieldName.AFTER);
    }

    @Override
    public String ceId() {
        // TODO: build composite ID from source fields (server name, changeId, txId, seq)
        return "";
    }

    @Override
    protected Set<String> connectorSpecificSourceFields() {
        return NEO4J_SOURCE_FIELDS;
    }
}
