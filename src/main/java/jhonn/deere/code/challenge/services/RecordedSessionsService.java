package jhonn.deere.code.challenge.services;

import jhonn.deere.code.challenge.dto.Session;
import jhonn.deere.code.challenge.entities.RecordedSession;
import jhonn.deere.code.challenge.repositories.RecordedSessionRepository;
import jhonn.deere.code.challenge.services.SQSHandlers.SQSReaderService;
import jhonn.deere.code.challenge.services.SQSHandlers.SQSWriterService;
import jhonn.deere.code.challenge.services.utils.ExternalSessionValidator;
import jhonn.deere.code.challenge.services.utils.Json_message_utils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecordedSessionsService {
    private static final Logger logger = Logger.getLogger(RecordedSessionsService.class);
    private final RecordedSessionRepository recordedSessionRepository;
    private final SQSWriterService sqsWriterService;
    private final SQSReaderService sqsReaderService;

    // THIS WOULD USUALLY BE Called by a Batch process or a Message handler from SQS on recieved message or a RabbitMSQ handler.
    public RecordedSessionsService(final RecordedSessionRepository recordedSessionRepository,
                                   final SQSWriterService sqsWriterService,
                                   final SQSReaderService sqsReaderService) {
        this.recordedSessionRepository = recordedSessionRepository;
        this.sqsWriterService = sqsWriterService;
        this.sqsReaderService = sqsReaderService;
    }

    public Map<String, String> ejecuteProcess() {
        // I'm doing this only so there is feedback on  executing the endpoint.
        final Map<String, String> responses = new HashedMap<>();
        logger.info("APP_ENTRY_POINT");
        try {
            // CONNECT WITH SQS
            // RECOVER AND TRANSLATE SQS1 MESSAGES
            java.util.List<Session> sqsresponses = Json_message_utils.parseMessages(sqsReaderService.recoverMessagesMock());
            // VERIFY SQS RESPONSE
            for (final Session session : sqsresponses) {
                // IF NOT PREVIOUSLY ON THE DATABASE AND VERIFIED OK SEND TO SQS2.
                if (
                        recordedSessionRepository.findBySessionGuidAndMachineIdAndSequenceNumber(session.getSessionGuid(), session.getMachineId(), session.getSequenceNumber()).isEmpty() &&
                                ExternalSessionValidator.validateMachineMock(session.getMachineId())) {
                    sqsWriterService.sendMessageMock(Json_message_utils.createJsonFromSession(session));

                    responses.put(session.getSessionGuid() + "_" + session.getSequenceNumber(), "OK");
                    // SAVE TO DATABASE INTERACTION.
                    recordedSessionRepository.save(new RecordedSession(session.getSessionGuid(), session.getMachineId(), session.getSequenceNumber()));
                } else {
                    responses.put(session.getSessionGuid() + "_" + session.getSequenceNumber(), "KO");
                }
            }
        } catch (final Exception ex) {
            // I usually define an Exception type with a message and push the previously written message, but this will suffice.
            logger.error("Error in the main app", ex);
        }
        return responses;
    }
}