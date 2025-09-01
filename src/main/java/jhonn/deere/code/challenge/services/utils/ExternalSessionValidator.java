package jhonn.deere.code.challenge.services.utils;

import jhonn.deere.code.challenge.services.utils.Json_message_utils;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExternalSessionValidator {
    private static final Logger logger = Logger.getLogger(Json_message_utils.class);

    public static boolean validateMachine(final int machineId) {
        try {
            final String json = "{ \"machineId\":\"%s\"}";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://api.example.com/endpoint"))
                    // THIS SHOULD BE AN ENVIRONMENT VARIABLE in a Service for a Springboot app for example.
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest
                            .BodyPublishers
                            .ofString(String.format(json, machineId)))  // replace "post_data" with your data
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Response status code: " + response.statusCode());
            logger.info("Response body: " + response.body());

            return (response.statusCode() == 200);
        } catch (Exception ex) {
            logger.error("Error calling the validator", ex);
        }
        return Boolean.FALSE;
    }

    public static boolean validateMachineMock(final int machineId) {
        return Boolean.TRUE;
    }


}
