package jhonn.deere.code.challenge;


import jhonn.deere.code.challenge.dto.Session;
import jhonn.deere.code.challenge.entities.RecordedSession;
import jhonn.deere.code.challenge.repositories.RecordedSessionRepository;
import jhonn.deere.code.challenge.services.MachineValidationService;
import jhonn.deere.code.challenge.services.RecordedSessionsService;
import jhonn.deere.code.challenge.services.SQSHandlers.SQSWriterService;
import jhonn.deere.code.challenge.services.utils.Json_message_utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=sa",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
public class ControllerIntegrationTest {

    protected static final String FIRST_DATASET = "{sessionGuid: \"a65de8c4-6385-4008-be36-5df0c5104fd5\",sequenceNumber: 1,machineId: 1,data: [{type: \"distance\",unit: \"m\",value: \"100\"},{type: \"workedSurface\",unit: \"m2\",value: \"600\"}]}";

    @Mock
    private RecordedSessionRepository recordedSessionRepository;

    @Mock
    private SQSWriterService sqsWriterService;

    @Mock
    private MachineValidationService machineValidationService;

    @Test
    void testExecuteProcess() {
        List<Session> sessions = Json_message_utils.parseMessages(List.of(
                Message.builder()
                        .messageId(String.valueOf(UUID.randomUUID()))
                        .body(FIRST_DATASET)
                        .build()));

        when(machineValidationService.validateMachineMock(any(Integer.class), anyBoolean())).thenReturn(true);

        RecordedSessionsService recordedSessionsService =
                new RecordedSessionsService(recordedSessionRepository, sqsWriterService, machineValidationService);

        recordedSessionsService.ejecuteProcess(sessions, true);

        verify(machineValidationService, times(1)).validateMachineMock(any(Integer.class), anyBoolean());
        verify(sqsWriterService, times(1)).sendMessageMock(anyString());
        verify(recordedSessionRepository, times(1)).save(any(RecordedSession.class));
    }

}
