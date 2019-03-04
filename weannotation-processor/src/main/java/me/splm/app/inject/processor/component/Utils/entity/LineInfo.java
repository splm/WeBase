package me.splm.app.inject.processor.component.Utils.entity;

public class LineInfo {
    private int row;
    private int globalIndex;
    private String key;
    private String value;

    public LineInfo(int row, int globalIndex, String key, String value) {
        this.row = row;
        this.globalIndex = globalIndex;
        this.key = key;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getGlobalIndex() {
        return globalIndex;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
