/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import io.debezium.pipeline.notification.NotificationService;
import io.debezium.pipeline.source.spi.ChangeEventSourceFactory;
import io.debezium.pipeline.source.spi.SnapshotChangeEventSource;
import io.debezium.pipeline.source.spi.SnapshotProgressListener;
import io.debezium.pipeline.source.spi.StreamingChangeEventSource;

/**
 * Factory for creating {@link io.debezium.pipeline.source.spi.ChangeEventSource}s specific to the Neo4j connector.
 *
 */
public class Neo4jChangeEventSourceFactory
        implements ChangeEventSourceFactory<Neo4jPartition, Neo4jOffsetContext> {

    private final Neo4jConnectorConfig connectorConfig;
    private final Neo4jConnection connection;

    public Neo4jChangeEventSourceFactory(Neo4jConnectorConfig connectorConfig,
                                         Neo4jConnection connection) {
        this.connectorConfig = connectorConfig;
        this.connection = connection;
    }

    @Override
    public SnapshotChangeEventSource<Neo4jPartition, Neo4jOffsetContext> getSnapshotChangeEventSource(
            SnapshotProgressListener<Neo4jPartition> snapshotProgressListener,
            NotificationService<Neo4jPartition, Neo4jOffsetContext> notificationService) {
        return new Neo4jSnapshotChangeEventSource(connectorConfig, connection);
    }

    @Override
    public StreamingChangeEventSource<Neo4jPartition, Neo4jOffsetContext> getStreamingChangeEventSource() {
        return new Neo4jStreamingChangeEventSource(connectorConfig, connection);
    }
}
