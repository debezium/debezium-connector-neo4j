/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigValue;
import org.apache.kafka.connect.connector.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.debezium.DebeziumException;
import io.debezium.config.Configuration;
import io.debezium.connector.common.BaseSourceConnector;

/**
 * A Kafka Connect source connector that creates tasks that read the Neo4j CDC change stream and generate the corresponding
 * data change events.
 * This connector is configured with the set of properties described in {@link Neo4jConnectorConfig}.
 *
 */
public class Neo4jConnector extends BaseSourceConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(Neo4jConnector.class);

    private Configuration config;

    @Override
    public String version() {
        return Module.version();
    }

    @Override
    public Class<? extends Task> taskClass() {
        return Neo4jConnectorTask.class;
    }

    @Override
    public void start(Map<String, String> props) {
        final Configuration config = Configuration.from(props);
        if (!config.validateAndRecord(Neo4jConnectorConfig.ALL_FIELDS, LOGGER::error)) {
            throw new DebeziumException("Error configuring an instance of "
                    + getClass().getSimpleName() + "; check the logs for details");
        }
        this.config = config;
        LOGGER.info("Successfully started Neo4j connector");
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        if (config == null) {
            LOGGER.error("Configuring a maximum of {} tasks with no connector configuration available", maxTasks);
            return Collections.emptyList();
        }
        return List.of(config.asMap());
    }

    @Override
    public void stop() {
        LOGGER.info("Stopping Neo4j connector");
        this.config = null;
    }

    @Override
    public ConfigDef config() {
        return Neo4jConnectorConfig.configDef();
    }

    @Override
    protected Map<String, ConfigValue> validateAllFields(Configuration config) {
        return config.validate(Neo4jConnectorConfig.ALL_FIELDS);
    }
}
