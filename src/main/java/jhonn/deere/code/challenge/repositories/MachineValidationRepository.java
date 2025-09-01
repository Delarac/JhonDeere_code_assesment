package jhonn.deere.code.challenge.repositories;

import jhonn.deere.code.challenge.entities.MachineValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineValidationRepository extends
        JpaRepository<MachineValidation, Long> {
    /**
     * This repo is only defined to save the Machine validation.
     */
}
