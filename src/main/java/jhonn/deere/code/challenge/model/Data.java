package jhonn.deere.code.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Data implements Serializable {
    @JsonProperty("type")
    private String type;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("value")
    private String value;

    public Data() {
        // Purposefully Empty
    }

    public Data(String type, String unit, String value) {
        this.type = type;
        this.unit = unit;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
