/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import io.debezium.connector.SnapshotRecord;
import io.debezium.pipeline.spi.OffsetContext;
import io.debezium.pipeline.txmetadata.TransactionContext;
import io.debezium.spi.schema.DataCollectionId;

/**
 * A context that facilitates the management of the current offset within the Neo4j CDC change stream.
 *
 */
public class Neo4jOffsetContext implements OffsetContext {

    private final SourceInfo sourceInfo;
    private final TransactionContext transactionContext;

    public Neo4jOffsetContext(SourceInfo sourceInfo, TransactionContext transactionContext) {
        this.sourceInfo = sourceInfo;
        this.transactionContext = transactionContext;
    }

    @Override
    public Map<String, ?> getOffset() {
        // TODO: serialize changeId and snapshot state into offset map
        return Collections.emptyMap();
    }

    @Override
    public Schema getSourceInfoSchema() {
        return sourceInfo.struct().schema();
    }

    @Override
    public Struct getSourceInfo() {
        return sourceInfo.struct();
    }

    @Override
    public boolean isInitialSnapshotRunning() {
        return false;
    }

    @Override
    public void markSnapshotRecord(SnapshotRecord record) {
        sourceInfo.setSnapshot(record);
    }

    @Override
    public void preSnapshotStart(boolean onDemand) {
    }

    @Override
    public void preSnapshotCompletion() {
    }

    @Override
    public void postSnapshotCompletion() {
    }

    @Override
    public void event(DataCollectionId collectionId, Instant timestamp) {
    }

    @Override
    public TransactionContext getTransactionContext() {
        return transactionContext;
    }

    public static class Loader implements OffsetContext.Loader<Neo4jOffsetContext> {

        private final Neo4jConnectorConfig connectorConfig;

        public Loader(Neo4jConnectorConfig connectorConfig) {
            this.connectorConfig = connectorConfig;
        }

        @Override
        public Neo4jOffsetContext load(Map<String, ?> offset) {
            // TODO: deserialize changeId from offset map, restore snapshot state
            SourceInfo sourceInfo = new SourceInfo(connectorConfig);
            return new Neo4jOffsetContext(sourceInfo, new TransactionContext());
        }
    }
}
