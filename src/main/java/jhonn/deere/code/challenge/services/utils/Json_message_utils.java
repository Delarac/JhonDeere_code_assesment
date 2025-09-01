package jhonn.deere.code.challenge.services.utils;

import com.google.gson.Gson;
import jhonn.deere.code.challenge.dto.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.ArrayList;
import java.util.List;

public class Json_message_utils {
    private static final Logger LOGGER = LogManager.getLogger(Json_message_utils.class);

    /**
     * Pareses from a message to a Session Object.
     * @param message
     * @return
     */
    public static Session extractSessionFromJson(final String message) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(message, Session.class);
        } catch (final Exception ex) {
            LOGGER.error("Error parsing the AWS SQS Message", ex);
            throw ex;
        }
    }

    /**
     * Parses from a Session to Json Object so it can be sent to Sqs2 based on the text requirements.
     * @param session
     * @return
     */
    public static String createJsonFromSession(final Session session) {
        try {
            Gson gson = new Gson();
            return gson.toJson(session);
        } catch (Exception ex) {
            LOGGER.error("Error converting the Session object to JSON", ex);
            throw ex;
        }
    }

    /**
     * Translates a list of messages, to a list of sessions, based on my interpretation of the input based on the text. 
     *
     * @param messages
     * @return
     */
    public static List<Session> parseMessages(final List<Message> messages) {
        final List<Session> sessionsResponse = new ArrayList<>();
        for (final Message message : messages) {
            sessionsResponse.add(extractSessionFromJson(message.body()));
        }
        return sessionsResponse;
    }

}
