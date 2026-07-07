/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Neo4jConnectorTest {

    @Test
    void versionShouldNotBeNull() {
        Neo4jConnector connector = new Neo4jConnector();
        assertThat(connector.version()).isNotNull();
        assertThat(connector.version()).isNotEmpty();
    }

    @Test
    void taskClassShouldReturnCorrectClass() {
        Neo4jConnector connector = new Neo4jConnector();
        assertThat(connector.taskClass()).isEqualTo(Neo4jConnectorTask.class);
    }

    @Test
    void configShouldReturnNonNullConfigDef() {
        Neo4jConnector connector = new Neo4jConnector();
        assertThat(connector.config()).isNotNull();
    }
}
