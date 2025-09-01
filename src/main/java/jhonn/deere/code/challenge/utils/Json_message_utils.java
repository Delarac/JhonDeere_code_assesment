package jhonn.deere.code.challenge.utils;

import com.google.gson.Gson;
import jhonn.deere.code.challenge.model.Session;
import org.apache.log4j.Logger;

public class Json_message_utils {
    private static final Logger logger = Logger.getLogger(Json_message_utils.class);
    public static Session translateMessage(final String message) {
        try{

        Gson gson = new Gson();
        return gson.fromJson(message, Session.class);

        }catch (final Exception ex){
            logger.error("Error parsing the AWS SQS Message", ex);
            throw ex;
        }

    }

}
