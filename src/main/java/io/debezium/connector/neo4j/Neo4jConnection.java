/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A wrapper around the Neo4j Java Driver for executing CDC and schema queries via the Bolt protocol.
 *
 */
public class Neo4jConnection implements AutoCloseable {

    public Neo4jConnection(Neo4jConnectorConfig config) {
        // TODO: create Neo4j Driver via GraphDatabase.driver() with AuthTokens.basic()
    }

    public void testConnection() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getCdcCurrent() {
        // TODO: call db.cdc.current() and return the change identifier
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getCdcEarliest() {
        // TODO: call db.cdc.earliest() and return the change identifier
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<?> queryCdcChanges(String fromChangeId) {
        // TODO: call db.cdc.query(from, selectors) and return the change records
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<String, List<String>> getKeyConstraints() {
        // TODO: run SHOW KEY CONSTRAINTS and return mapping of label/type -> key property names
        return Collections.emptyMap();
    }

    @Override
    public void close() {
    }
}
