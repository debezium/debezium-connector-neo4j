/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.neo4j;

import java.time.Instant;

import io.debezium.connector.common.BaseSourceInfo;

/**
 * Information about the source of information, which includes the position within the Neo4j CDC change stream.
 * The offset is identified by a {@code changeId}, an opaque cursor returned by the {@code db.cdc.query()} procedure.
 *
 */
public class SourceInfo extends BaseSourceInfo {

    public static final String CHANGE_ID_KEY = "changeId";
    public static final String TX_ID_KEY = "txId";
    public static final String SEQ_KEY = "seq";
    public static final String CAPTURE_MODE_KEY = "captureMode";

    private String changeId;
    private Long txId;
    private Integer seq;
    private String captureMode;
    private Instant timestamp;
    private final String database;

    public SourceInfo(Neo4jConnectorConfig config) {
        super(config);
        this.database = config.getNeo4jDatabase();
    }

    @Override
    protected Instant timestamp() {
        return timestamp;
    }

    @Override
    protected String database() {
        return database;
    }

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    public Long getTxId() {
        return txId;
    }

    public void setTxId(Long txId) {
        this.txId = txId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getCaptureMode() {
        return captureMode;
    }

    public void setCaptureMode(String captureMode) {
        this.captureMode = captureMode;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SourceInfo [changeId=" + changeId + ", txId=" + txId + ", seq=" + seq
                + ", captureMode=" + captureMode + ", database=" + database + "]";
    }
}
