package jhonn.deere.code.challenge.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;


@Entity(name = "machine_validation_recorder")
public class MachineValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime validationTime;
    private int machineId;
    private String status;

    public MachineValidation(LocalDateTime validationTime, int machineId, String status) {
        this.validationTime = validationTime;
        this.machineId = machineId;
        this.status = status;
    }

    public MachineValidation(Long id, LocalDateTime validationTime, int machineId, String status) {
        this.id = id;
        this.validationTime = validationTime;
        this.machineId = machineId;
        this.status = status;
    }

    public MachineValidation() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getValidationTime() {
        return validationTime;
    }

    public void setValidationTime(LocalDateTime validationTime) {
        this.validationTime = validationTime;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
