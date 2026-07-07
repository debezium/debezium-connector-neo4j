/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.debezium.config.Configuration;

class Neo4jConnectorConfigTest {

    @Test
    void snapshotModeShouldDefaultToInitial() {
        Configuration config = Configuration.create()
                .with(Neo4jConnectorConfig.NEO4J_URI, "bolt://localhost:7687")
                .with(Neo4jConnectorConfig.NEO4J_USER, "neo4j")
                .with(Neo4jConnectorConfig.NEO4J_PASSWORD, "password")
                .with(Neo4jConnectorConfig.NEO4J_DATABASE, "neo4j")
                .with(Neo4jConnectorConfig.TOPIC_PREFIX, "test-server")
                .build();

        Neo4jConnectorConfig connectorConfig = new Neo4jConnectorConfig(config);
        assertThat(connectorConfig.getSnapshotMode())
                .isEqualTo(Neo4jConnectorConfig.SnapshotMode.INITIAL);
    }

    @Test
    void shouldReturnConfiguredNeo4jProperties() {
        Configuration config = Configuration.create()
                .with(Neo4jConnectorConfig.NEO4J_URI, "bolt://myhost:7687")
                .with(Neo4jConnectorConfig.NEO4J_USER, "testuser")
                .with(Neo4jConnectorConfig.NEO4J_PASSWORD, "testpass")
                .with(Neo4jConnectorConfig.NEO4J_DATABASE, "mydb")
                .with(Neo4jConnectorConfig.NEO4J_ENCRYPTION, true)
                .with(Neo4jConnectorConfig.TOPIC_PREFIX, "test-server")
                .build();

        Neo4jConnectorConfig connectorConfig = new Neo4jConnectorConfig(config);
        assertThat(connectorConfig.getNeo4jUri()).isEqualTo("bolt://myhost:7687");
        assertThat(connectorConfig.getNeo4jUser()).isEqualTo("testuser");
        assertThat(connectorConfig.getNeo4jPassword()).isEqualTo("testpass");
        assertThat(connectorConfig.getNeo4jDatabase()).isEqualTo("mydb");
        assertThat(connectorConfig.isNeo4jEncryption()).isTrue();
    }

    @Test
    void contextNameShouldReturnNeo4j() {
        Configuration config = Configuration.create()
                .with(Neo4jConnectorConfig.NEO4J_URI, "bolt://localhost:7687")
                .with(Neo4jConnectorConfig.NEO4J_USER, "neo4j")
                .with(Neo4jConnectorConfig.NEO4J_PASSWORD, "password")
                .with(Neo4jConnectorConfig.NEO4J_DATABASE, "neo4j")
                .with(Neo4jConnectorConfig.TOPIC_PREFIX, "test-server")
                .build();

        Neo4jConnectorConfig connectorConfig = new Neo4jConnectorConfig(config);
        assertThat(connectorConfig.getContextName()).isEqualTo("Neo4j");
        assertThat(connectorConfig.getConnectorName()).isEqualTo("neo4j");
    }
}
