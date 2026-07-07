/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.kafka.connect.source.SourceRecord;

import io.debezium.config.CommonConnectorConfig;
import io.debezium.config.Configuration;
import io.debezium.config.Field;
import io.debezium.connector.common.BaseSourceTask;
import io.debezium.connector.common.CdcSourceTaskContext;
import io.debezium.pipeline.ChangeEventSourceCoordinator;
import io.debezium.pipeline.ErrorHandler;

/**
 * A Kafka Connect source task that captures changes from a Neo4j database using the CDC API.
 *
 * @see Neo4jConnector
 * @see Neo4jConnectorConfig
 */
public class Neo4jConnectorTask extends BaseSourceTask<Neo4jPartition, Neo4jOffsetContext> {

    private volatile Neo4jTaskContext taskContext;

    @Override
    public String version() {
        return Module.version();
    }

    @Override
    public CdcSourceTaskContext<? extends CommonConnectorConfig> preStart(Configuration config) {
        final Neo4jConnectorConfig connectorConfig = new Neo4jConnectorConfig(config);
        this.taskContext = new Neo4jTaskContext(config, connectorConfig);
        return taskContext;
    }

    @Override
    public ChangeEventSourceCoordinator<Neo4jPartition, Neo4jOffsetContext> start(Configuration config) {
        // TODO: wire up Neo4jConnection, ChangeEventQueue, ErrorHandler, EventDispatcher,
        //  SignalProcessor, SnapshotterService, Neo4jChangeEventSourceFactory,
        //  ChangeEventSourceCoordinator and start the coordinator.
        //  Use DefaultChangeEventSourceMetricsFactory for standard JMX metrics;
        //  if Neo4j-specific metrics are needed later, subclass it.
        //  Heartbeat: Neo4j uses Bolt (not JDBC), so DatabaseHeartbeatImpl won't work
        //  provide a no-op HeartbeatConnectionProvider or a custom one backed by a Cypher query.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected String connectorName() {
        return Module.name();
    }

    @Override
    public List<SourceRecord> doPoll() throws InterruptedException {
        return Collections.emptyList();
    }

    @Override
    protected Optional<ErrorHandler> getErrorHandler() {
        return Optional.empty();
    }

    @Override
    public void doStop() {
    }

    @Override
    protected Iterable<Field> getAllConfigurationFields() {
        return Neo4jConnectorConfig.ALL_FIELDS;
    }
}
