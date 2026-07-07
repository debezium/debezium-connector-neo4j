/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import java.util.Collections;

import io.debezium.pipeline.signal.actions.snapshotting.SnapshotConfiguration;
import io.debezium.pipeline.source.SnapshottingTask;
import io.debezium.pipeline.source.spi.SnapshotChangeEventSource;
import io.debezium.pipeline.spi.SnapshotResult;

/**
 * A {@link SnapshotChangeEventSource} that captures the current state of a Neo4j database
 * using Cypher {@code MATCH} queries for configured labels and relationship types.
 *
 */
public class Neo4jSnapshotChangeEventSource
        implements SnapshotChangeEventSource<Neo4jPartition, Neo4jOffsetContext> {

    public Neo4jSnapshotChangeEventSource(Neo4jConnectorConfig connectorConfig,
                                          Neo4jConnection connection) {
    }

    @Override
    public SnapshotResult<Neo4jOffsetContext> execute(ChangeEventSourceContext context,
                                                      Neo4jPartition partition,
                                                      Neo4jOffsetContext previousOffset,
                                                      SnapshottingTask snapshottingTask)
            throws InterruptedException {
        // TODO: implement Cypher-based snapshot (MATCH queries for captured labels and relationship types)
        return SnapshotResult.completed(previousOffset);
    }

    @Override
    public SnapshottingTask getSnapshottingTask(Neo4jPartition partition, Neo4jOffsetContext previousOffset) {
        return new SnapshottingTask(false, false, Collections.emptyList(), Collections.emptyMap(), false);
    }

    @Override
    public SnapshottingTask getBlockingSnapshottingTask(Neo4jPartition partition,
                                                         Neo4jOffsetContext previousOffset,
                                                         SnapshotConfiguration snapshotConfiguration) {
        return new SnapshottingTask(false, false, Collections.emptyList(), Collections.emptyMap(), false);
    }
}
