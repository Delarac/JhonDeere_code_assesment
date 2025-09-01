package jhonn.deere.code.challenge;

import jhonn.deere.code.challenge.model.Session;
import jhonn.deere.code.challenge.utils.Json_message_utils;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    static final String FIRST_DATASET = "{sessionGuid: \"a65de8c4-6385-4008-be36-5df0c5104fd5\",sequenceNumber: 1,machineId: 1,data: [{type: \"distance\",unit: \"m\",value: \"100\"},{type: \"workedSurface\",unit: \"m2\",value: \"600\"}]}";
    static final String SECOND_DATASET = "{sessionGuid: \"a65de8c4-6385-4008-be36-5df0c5104fd5\",sequenceNumber: 2,machineId: 1,data: [{type: \"distance\",unit: \"m\",value: \"102\"},{type: \"workedSurface\",unit: \"m2\",value: \"610\"}]}";


    public static void main(String[] args) {
        logger.debug("APP_ENTRY_POINT");
        try {

            final SQSReader sqsReader = new SQSReader();
            final SQSWriter sqsWriter = new SQSWriter();
            sqsReader.recoverMessagesMock();
            java.util.List<Session> SQSResponses = Json_message_utils.parseMessages(sqsReader.recoverMessagesMock());


            sqsWriter.sendMessageMock("");

        } catch (final Exception ex) {
            logger.error("Error occurred while recovering messages", ex);
        }

    }

}
