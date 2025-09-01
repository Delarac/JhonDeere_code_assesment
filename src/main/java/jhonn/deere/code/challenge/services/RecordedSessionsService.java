package jhonn.deere.code.challenge.services;

import jhonn.deere.code.challenge.dto.Session;
import jhonn.deere.code.challenge.entities.RecordedSession;
import jhonn.deere.code.challenge.repositories.RecordedSessionRepository;
import jhonn.deere.code.challenge.services.SQSHandlers.SQSWriterService;
import jhonn.deere.code.challenge.services.utils.Json_message_utils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecordedSessionsService {
    private static final Logger LOGGER = LogManager.getLogger(Json_message_utils.class);
    private final RecordedSessionRepository recordedSessionRepository;
    private final SQSWriterService sqsWriterService;
    private final MachineValidationService machineValidationService;

    // THIS WOULD USUALLY BE Called by a Batch process or a Message handler from SQS on recieved message or a RabbitMSQ handler.
    public RecordedSessionsService(final RecordedSessionRepository recordedSessionRepository,
                                   final SQSWriterService sqsWriterService,
                                   final MachineValidationService machineValidationService) {
        this.recordedSessionRepository = recordedSessionRepository;
        this.sqsWriterService = sqsWriterService;
        this.machineValidationService = machineValidationService;
    }

    public Map<String, String> ejecuteProcess(final List<Session> sqsresponses, // List here so Unit tests are easier, but should be divided on 2 methods, one to list and one to validate.
                                              final boolean validationMock) { // Validation mock shouldn't exist...
        // I'm doing this only so there is feedback on  executing the endpoint.
        final Map<String, String> responses = new HashedMap<>();
        LOGGER.info("APP_ENTRY_POINT");
        try {
            // VERIFY SQS RESPONSE
            for (final Session session : sqsresponses) {
                // IF NOT PREVIOUSLY ON THE DATABASE AND VERIFIED OK SEND TO SQS2.
                if (machineValidationService.validateMachineMock(
                        session.getMachineId(),
                        validationMock)
                        &&
                        recordedSessionRepository.findBySessionGuidAndMachineIdAndSequenceNumber(
                                session.getSessionGuid(),
                                session.getMachineId(),
                                session.getSequenceNumber()).isEmpty()
                ) {
                    sqsWriterService.sendMessageMock(Json_message_utils.createJsonFromSession(session));
                    responses.put(session.getSessionGuid() + "_" + session.getSequenceNumber(), "OK");
                    // SAVE TO DATABASE INTERACTION.
                    recordedSessionRepository.save(
                            new RecordedSession(
                                    session.getSessionGuid(),
                                    session.getMachineId(),
                                    session.getSequenceNumber()));
                } else {
                    responses.put(session.getSessionGuid() + "_" + session.getSequenceNumber(), "KO");
                }
            }
        } catch (final Exception ex) {
            // I usually define an Exception type with a message and push the previously written message, but this will suffice.
            LOGGER.error("Error in the main app", ex);
        }
        return responses;
    }
}