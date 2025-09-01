package jhonn.deere.code.challenge.controllers;

import jhonn.deere.code.challenge.services.RecordedSessionsService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger logger = Logger.getLogger(TestController.class);

    private RecordedSessionsService recordedSessionsService;

    public TestController(final RecordedSessionsService recordedSessionsService) {
        this.recordedSessionsService = recordedSessionsService;
    }

    // THIS ONLY EXISTS SO THAT I CAN MAKE A EXECUTION FROM AN ENDPOINT, THIS WHOLE THING SHOULD BE A BATCH Or ON demand message handler program...
    @GetMapping("/execute")
    public ResponseEntity<String> executeAction() {
        // ON DEMAND EXECUTION OF TEST CASE
        logger.info("StartExecutionOfBaseLineTest");
        final String responseMessage = recordedSessionsService.ejecuteProcess().toString();
        return ResponseEntity.ok(responseMessage);
    }


}
