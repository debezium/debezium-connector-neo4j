/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.SchemaBuilder;

import io.debezium.relational.Column;
import io.debezium.relational.ValueConverter;
import io.debezium.relational.ValueConverterProvider;

/**
 * Converts Neo4j property values to Kafka Connect schema types.
 */
public class Neo4jValueConverter implements ValueConverterProvider {

    @Override
    public SchemaBuilder schemaBuilder(Column column) {
        return null;
    }

    @Override
    public ValueConverter converter(Column column, Field fieldDefn) {
        return ValueConverter.passthrough();
    }
}
