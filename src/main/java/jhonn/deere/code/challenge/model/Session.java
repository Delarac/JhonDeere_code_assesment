package jhonn.deere.code.challenge.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Session implements Serializable {
    @SerializedName("sessionGuid")
    private String sessionGuid;
    @SerializedName("sequenceNumber")
    private int sequenceNumber;
    @SerializedName("machineId")
    private int machineId;
    @SerializedName("data")
    private List<Data> data;

    public Session() {
        // Purposefully empty
    }

    public Session(String sessionGuid, int sequenceNumber, int machineId, List<Data> data) {
        this.sessionGuid = sessionGuid;
        this.sequenceNumber = sequenceNumber;
        this.machineId = machineId;
        this.data = data;
    }

    public String getSessionGuid() {
        return sessionGuid;
    }

    public void setSessionGuid(String sessionGuid) {
        this.sessionGuid = sessionGuid;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}