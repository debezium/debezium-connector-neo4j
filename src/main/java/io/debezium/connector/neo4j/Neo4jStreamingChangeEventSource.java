/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.debezium.pipeline.source.spi.StreamingChangeEventSource;
import io.debezium.util.Metronome;

/**
 * A {@link StreamingChangeEventSource} that polls the Neo4j CDC API ({@code db.cdc.query()})
 * for new change events on a configurable interval.
 *
 */
public class Neo4jStreamingChangeEventSource
        implements StreamingChangeEventSource<Neo4jPartition, Neo4jOffsetContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Neo4jStreamingChangeEventSource.class);

    private final Neo4jConnectorConfig connectorConfig;
    private final Neo4jConnection connection;

    public Neo4jStreamingChangeEventSource(Neo4jConnectorConfig connectorConfig,
                                           Neo4jConnection connection) {
        this.connectorConfig = connectorConfig;
        this.connection = connection;
    }

    @Override
    public void execute(ChangeEventSourceContext context,
                        Neo4jPartition partition,
                        Neo4jOffsetContext offsetContext)
            throws InterruptedException {
        final Metronome metronome = Metronome.sleeper(connectorConfig.getPollInterval(), io.debezium.util.Clock.SYSTEM);
        LOGGER.info("Starting Neo4j streaming change event source");
        // TODO: poll db.cdc.query() on each iteration, process change events, emit to dispatcher
        while (context.isRunning()) {
            metronome.pause();
        }
    }
}
