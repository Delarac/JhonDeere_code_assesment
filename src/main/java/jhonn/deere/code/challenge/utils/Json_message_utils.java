package jhonn.deere.code.challenge.utils;

import com.google.gson.Gson;
import jhonn.deere.code.challenge.model.Session;
import org.apache.log4j.Logger;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.ArrayList;
import java.util.List;

public class Json_message_utils {
    private static final Logger logger = Logger.getLogger(Json_message_utils.class);

    public static Session translateMessage(final String message) {
        try {

            Gson gson = new Gson();
            return gson.fromJson(message, Session.class);

        } catch (final Exception ex) {
            logger.error("Error parsing the AWS SQS Message", ex);
            throw ex;
        }

    }

    public static List<Session> parseMessages(final List<Message> messages) {
        final List<Session> sessionsResponse = new ArrayList<>();
        for (final Message message : messages) {
            sessionsResponse.add(translateMessage(message.body()));
        }
        return sessionsResponse;
    }

}
