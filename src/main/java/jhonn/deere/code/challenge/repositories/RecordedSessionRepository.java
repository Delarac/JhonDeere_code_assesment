package jhonn.deere.code.challenge.repositories;

import jhonn.deere.code.challenge.entities.RecordedSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordedSessionRepository extends
        JpaRepository<RecordedSession, Long> {

    Optional<RecordedSession> findBySessionGuidAndMachineIdAndSequenceNumber(final String sessionGuid,
                                                                             final int machineId,
                                                                             final int sequenceNumber);

}
