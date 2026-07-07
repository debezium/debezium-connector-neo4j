/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import java.io.IOException;
import java.util.Set;

import io.debezium.connector.base.ChangeEventQueue;
import io.debezium.pipeline.ErrorHandler;
import io.debezium.util.Collect;

/**
 * Error handler for Neo4j.
 *
 */
public class Neo4jErrorHandler extends ErrorHandler {

    public Neo4jErrorHandler(Neo4jConnectorConfig connectorConfig,
                             ChangeEventQueue<?> queue,
                             ErrorHandler replacedErrorHandler) {
        super(Neo4jConnector.class, connectorConfig, queue, replacedErrorHandler);
    }

    @Override
    protected Set<Class<? extends Exception>> communicationExceptions() {
        return Collect.unmodifiableSet(IOException.class);
    }
}
