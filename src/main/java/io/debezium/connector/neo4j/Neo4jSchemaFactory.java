/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import io.debezium.schema.SchemaFactory;

/**
 */
public class Neo4jSchemaFactory extends SchemaFactory {

    private static final Neo4jSchemaFactory INSTANCE = new Neo4jSchemaFactory();

    public Neo4jSchemaFactory() {
        super();
    }

    public static Neo4jSchemaFactory get() {
        return INSTANCE;
    }
}
