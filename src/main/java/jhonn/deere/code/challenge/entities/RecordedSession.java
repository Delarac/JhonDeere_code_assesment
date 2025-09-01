package jhonn.deere.code.challenge.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "recorded_sessions")
public class RecordedSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sessionGuid;
    private int machineId;
    private int sequenceNumber;

    public RecordedSession() {

    }

    public RecordedSession(String sessionGuid,
                           int machineId,
                           int sequenceNumber) {
        this.sessionGuid = sessionGuid;
        this.machineId = machineId;
        this.sequenceNumber = sequenceNumber;
    }


    public RecordedSession(Long id, String sessionGuid, int machineId) {
        this.id = id;
        this.sessionGuid = sessionGuid;
        this.machineId = machineId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionGuid() {
        return sessionGuid;
    }

    public void setSessionGuid(String sessionGuid) {
        this.sessionGuid = sessionGuid;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
