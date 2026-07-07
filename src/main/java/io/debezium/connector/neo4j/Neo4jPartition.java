/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.debezium.pipeline.spi.Partition;
import io.debezium.util.Collect;

/**
 * Represents the source partition used by the Neo4j connector.
 *
 */
public class Neo4jPartition implements Partition {

    private static final String SERVER_ID_KEY = "server_id";

    private final String serverId;

    public Neo4jPartition(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public Map<String, String> getSourcePartition() {
        return Collect.hashMapOf(SERVER_ID_KEY, serverId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Neo4jPartition other = (Neo4jPartition) obj;
        return Objects.equals(serverId, other.serverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverId);
    }

    @Override
    public String toString() {
        return "Neo4jPartition [sourcePartition=" + getSourcePartition() + "]";
    }

    public static class Provider implements Partition.Provider<Neo4jPartition> {

        private final String logicalName;

        public Provider(Neo4jConnectorConfig connectorConfig) {
            this.logicalName = connectorConfig.getLogicalName();
        }

        @Override
        public Set<Neo4jPartition> getPartitions() {
            return Set.of(new Neo4jPartition(logicalName));
        }
    }
}
