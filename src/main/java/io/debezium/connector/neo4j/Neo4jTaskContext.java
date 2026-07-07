/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import io.debezium.config.Configuration;
import io.debezium.connector.common.CdcSourceTaskContext;

/**
 */
public class Neo4jTaskContext extends CdcSourceTaskContext<Neo4jConnectorConfig> {

    public Neo4jTaskContext(Configuration rawConfig, Neo4jConnectorConfig config) {
        super(rawConfig, config, config.getCustomMetricTags());
    }
}
