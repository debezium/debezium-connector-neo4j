/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import java.util.List;
import java.util.Optional;

import org.apache.kafka.common.config.ConfigDef;

import io.debezium.config.CommonConnectorConfig;
import io.debezium.config.ConfigDefinition;
import io.debezium.config.Configuration;
import io.debezium.config.EnumeratedValue;
import io.debezium.config.Field;
import io.debezium.connector.AbstractSourceInfo;
import io.debezium.connector.SourceInfoStructMaker;

/**
 * The configuration properties for the Neo4j connector.
 *
 */
public class Neo4jConnectorConfig extends CommonConnectorConfig {

    public static final Field NEO4J_URI = Field.create("neo4j.uri")
            .withDisplayName("Neo4j URI")
            .withType(ConfigDef.Type.STRING)
            .withWidth(ConfigDef.Width.LONG)
            .withImportance(ConfigDef.Importance.HIGH)
            .withDescription("Neo4j Bolt connection URI (e.g. bolt://localhost:7687 or neo4j://cluster:7687).")
            .required();

    public static final Field NEO4J_USER = Field.create("neo4j.user")
            .withDisplayName("Neo4j user")
            .withType(ConfigDef.Type.STRING)
            .withWidth(ConfigDef.Width.SHORT)
            .withImportance(ConfigDef.Importance.HIGH)
            .withDescription("Neo4j authentication user.")
            .required();

    public static final Field NEO4J_PASSWORD = Field.create("neo4j.password")
            .withDisplayName("Neo4j password")
            .withType(ConfigDef.Type.PASSWORD)
            .withWidth(ConfigDef.Width.SHORT)
            .withImportance(ConfigDef.Importance.HIGH)
            .withDescription("Neo4j authentication password.")
            .required();

    public static final Field NEO4J_DATABASE = Field.create("neo4j.database")
            .withDisplayName("Neo4j database")
            .withType(ConfigDef.Type.STRING)
            .withWidth(ConfigDef.Width.MEDIUM)
            .withImportance(ConfigDef.Importance.HIGH)
            .withDescription("The Neo4j database name to capture changes from.")
            .required();

    public static final Field NEO4J_ENCRYPTION = Field.create("neo4j.encryption")
            .withDisplayName("Neo4j encryption")
            .withType(ConfigDef.Type.BOOLEAN)
            .withWidth(ConfigDef.Width.SHORT)
            .withImportance(ConfigDef.Importance.MEDIUM)
            .withDefault(false)
            .withDescription("Enable TLS encryption for the Bolt connection.");

    public static final Field NODE_LABEL_INCLUDE_LIST = Field.create("node.label.include.list")
            .withDisplayName("Include node labels")
            .withType(ConfigDef.Type.LIST)
            .withWidth(ConfigDef.Width.LONG)
            .withImportance(ConfigDef.Importance.MEDIUM)
            .withDescription("Comma-separated list of node labels to capture. Empty means all labels.");

    public static final Field NODE_LABEL_EXCLUDE_LIST = Field.create("node.label.exclude.list")
            .withDisplayName("Exclude node labels")
            .withType(ConfigDef.Type.LIST)
            .withWidth(ConfigDef.Width.LONG)
            .withImportance(ConfigDef.Importance.MEDIUM)
            .withDescription("Comma-separated list of node labels to exclude.");

    public static final Field RELATIONSHIP_TYPE_INCLUDE_LIST = Field.create("relationship.type.include.list")
            .withDisplayName("Include relationship types")
            .withType(ConfigDef.Type.LIST)
            .withWidth(ConfigDef.Width.LONG)
            .withImportance(ConfigDef.Importance.MEDIUM)
            .withDescription("Comma-separated list of relationship types to capture. Empty means all types.");

    public static final Field RELATIONSHIP_TYPE_EXCLUDE_LIST = Field.create("relationship.type.exclude.list")
            .withDisplayName("Exclude relationship types")
            .withType(ConfigDef.Type.LIST)
            .withWidth(ConfigDef.Width.LONG)
            .withImportance(ConfigDef.Importance.MEDIUM)
            .withDescription("Comma-separated list of relationship types to exclude.");

    public static final Field PROPERTY_INCLUDE_LIST = Field.create("property.include.list")
            .withDisplayName("Include properties")
            .withType(ConfigDef.Type.LIST)
            .withWidth(ConfigDef.Width.LONG)
            .withImportance(ConfigDef.Importance.MEDIUM)
            .withDescription("Comma-separated list of properties to include in change events. Empty means all properties.");

    public static final Field PROPERTY_EXCLUDE_LIST = Field.create("property.exclude.list")
            .withDisplayName("Exclude properties")
            .withType(ConfigDef.Type.LIST)
            .withWidth(ConfigDef.Width.LONG)
            .withImportance(ConfigDef.Importance.MEDIUM)
            .withDescription("Comma-separated list of properties to exclude from change events.");

    public static final Field MESSAGE_KEY_PROPERTIES = Field.create("message.key.properties")
            .withDisplayName("Message key properties")
            .withType(ConfigDef.Type.STRING)
            .withWidth(ConfigDef.Width.LONG)
            .withImportance(ConfigDef.Importance.MEDIUM)
            .withDescription("Override event key for specific labels/types. Format: node.Label:prop1,prop2;relationship.TYPE:prop3");

    public static final Field SNAPSHOT_MODE = Field.create(SNAPSHOT_MODE_PROPERTY_NAME)
            .withDisplayName("Snapshot mode")
            .withEnum(SnapshotMode.class, SnapshotMode.INITIAL)
            .withWidth(ConfigDef.Width.SHORT)
            .withImportance(ConfigDef.Importance.LOW)
            .withDescription("Snapshot mode: 'initial' (snapshot then stream), 'never' (stream only), 'initial_only' (snapshot without streaming).");

    private static final Field SOURCE_INFO_STRUCT_MAKER = CommonConnectorConfig.SOURCE_INFO_STRUCT_MAKER
            .withDefault(Neo4jSourceInfoStructMaker.class.getName());

    private static final ConfigDefinition CONFIG_DEFINITION = ConfigDefinition.editor()
            .name("Neo4j")
            .type(
                    NEO4J_URI,
                    NEO4J_USER,
                    NEO4J_PASSWORD,
                    NEO4J_DATABASE,
                    NEO4J_ENCRYPTION)
            .connector(
                    NODE_LABEL_INCLUDE_LIST,
                    NODE_LABEL_EXCLUDE_LIST,
                    RELATIONSHIP_TYPE_INCLUDE_LIST,
                    RELATIONSHIP_TYPE_EXCLUDE_LIST,
                    PROPERTY_INCLUDE_LIST,
                    PROPERTY_EXCLUDE_LIST,
                    MESSAGE_KEY_PROPERTIES,
                    SNAPSHOT_MODE)
            .events(
                    SOURCE_INFO_STRUCT_MAKER)
            .create();

    protected static final Field.Set ALL_FIELDS = Field.setOf(CONFIG_DEFINITION.all());

    private final SnapshotMode snapshotMode;
    private final Configuration config;

    public Neo4jConnectorConfig(Configuration config) {
        super(config, 0);
        this.config = config;
        this.snapshotMode = SnapshotMode.parse(config.getString(SNAPSHOT_MODE), SNAPSHOT_MODE.defaultValueAsString());
    }

    public static ConfigDef configDef() {
        return CONFIG_DEFINITION.configDef();
    }

    public String getNeo4jUri() {
        return config.getString(NEO4J_URI);
    }

    public String getNeo4jUser() {
        return config.getString(NEO4J_USER);
    }

    public String getNeo4jPassword() {
        return config.getString(NEO4J_PASSWORD);
    }

    public String getNeo4jDatabase() {
        return config.getString(NEO4J_DATABASE);
    }

    public boolean isNeo4jEncryption() {
        return config.getBoolean(NEO4J_ENCRYPTION);
    }

    public List<String> getNodeLabelIncludeList() {
        return config.getList(NODE_LABEL_INCLUDE_LIST);
    }

    public List<String> getNodeLabelExcludeList() {
        return config.getList(NODE_LABEL_EXCLUDE_LIST);
    }

    public List<String> getRelationshipTypeIncludeList() {
        return config.getList(RELATIONSHIP_TYPE_INCLUDE_LIST);
    }

    public List<String> getRelationshipTypeExcludeList() {
        return config.getList(RELATIONSHIP_TYPE_EXCLUDE_LIST);
    }

    public List<String> getPropertyIncludeList() {
        return config.getList(PROPERTY_INCLUDE_LIST);
    }

    public List<String> getPropertyExcludeList() {
        return config.getList(PROPERTY_EXCLUDE_LIST);
    }

    public String getMessageKeyProperties() {
        return config.getString(MESSAGE_KEY_PROPERTIES);
    }

    @Override
    public String getContextName() {
        return Module.contextName();
    }

    @Override
    public String getConnectorName() {
        return Module.name();
    }

    @Override
    public EnumeratedValue getSnapshotMode() {
        return snapshotMode;
    }

    @Override
    public Optional<EnumeratedValue> getSnapshotLockingMode() {
        return Optional.empty();
    }

    @Override
    protected SourceInfoStructMaker<?> getSourceInfoStructMaker(Version version) {
        return getSourceInfoStructMaker(SOURCE_INFO_STRUCT_MAKER, Module.name(), Module.version(), this);
    }

    public enum SnapshotMode implements EnumeratedValue {
        INITIAL("initial"),
        NEVER("never"),
        INITIAL_ONLY("initial_only");

        private final String value;

        SnapshotMode(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public static SnapshotMode parse(String value) {
            return parse(value, null);
        }

        public static SnapshotMode parse(String value, String defaultValue) {
            SnapshotMode mode = find(value);
            if (mode == null && defaultValue != null) {
                mode = find(defaultValue);
            }
            return mode;
        }

        private static SnapshotMode find(String value) {
            if (value == null) {
                return null;
            }
            value = value.trim();
            for (SnapshotMode option : SnapshotMode.values()) {
                if (option.getValue().equalsIgnoreCase(value)) {
                    return option;
                }
            }
            return null;
        }
    }
}
