package jhonn.deere.code.challenge;

import jhonn.deere.code.challenge.model.Session;
import jhonn.deere.code.challenge.utils.Json_message_utils;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("APP_ENTRY_POINT");
        try {
            // CONNECT WITH SQS
            final SQSReader sqsReader = new SQSReader();
            final SQSWriter sqsWriter = new SQSWriter();
            // RECOVER AND TRANSLATE SQS1 MESSAGES
            java.util.List<Session> sqsresponses = Json_message_utils.parseMessages(sqsReader.recoverMessagesMock());
            // VERIFY SQS RESPONSE
                for(final Session session : sqsresponses){
                    // IF NOT PREVIOUSLY ON THE DATABASE AND VERIFIED OK SEND TO SQS2.
                    if( //VALIDAR NO PREVIO BD
                        ExternalSessionValidator.validateMachineMock(session.getMachineId())){
                        sqsWriter.sendMessageMock(Json_message_utils.createJsonFromSession(session));
                    }
                    // SAVE TO DATABASE INTERACTION.


                }

        } catch (final Exception ex) {
            // I usually define an Exception type with a message and push the previously written message, but this will suffice.
            logger.error("Error in the main app", ex);
        }

        logger.info("APP_EXIT_POINT");
    }

}
