package jhonn.deere.code.challenge.controllers;

import jhonn.deere.code.challenge.services.RecordedSessionsService;
import jhonn.deere.code.challenge.services.SQSHandlers.SQSReaderService;
import jhonn.deere.code.challenge.services.utils.Json_message_utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger LOGGER = LogManager.getLogger(TestController.class);
    private final RecordedSessionsService recordedSessionsService;
    private final SQSReaderService sqsReaderService;

    public TestController(final RecordedSessionsService recordedSessionsService,
                          final SQSReaderService sqsReaderService) {
        this.recordedSessionsService = recordedSessionsService;
        this.sqsReaderService = sqsReaderService;
    }

    /**
     * This method exists exclusively to execute the program on demand, and test the 2 cases defined on the text.
     *  The whole of the program should be defined as a batch process or an on demand message handler.
     *  If it where a Batch process, there could be multiple instances of itself running based on parameters which would make it more expensive on AWS, but way faster also.
     *
     * @return Returns a String to the front with each test case result. Shouldn't exist.
     */
    @GetMapping("/execute")
    public ResponseEntity<String> executeAction() {
        // ON DEMAND EXECUTION OF TEST CASE
        LOGGER.info("StartExecutionOfBaseLineTest");
        // CONNECT WITH SQS
        // RECOVER AND TRANSLATE SQS1 MESSAGES
        final String responseMessage = recordedSessionsService.ejecuteProcess(
                Json_message_utils.parseMessages(sqsReaderService.recoverMessagesMock()), Boolean.TRUE).toString();
        return ResponseEntity.ok(responseMessage);
    }






}
