package jhonn.deere.code.challenge.services;

import jhonn.deere.code.challenge.entities.MachineValidation;
import jhonn.deere.code.challenge.repositories.MachineValidationRepository;
import jhonn.deere.code.challenge.services.utils.Json_message_utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Service
public class MachineValidationService {
    private static final Logger LOGGER = LogManager.getLogger(Json_message_utils.class);

    private final MachineValidationRepository machineValidationRepository;

    public MachineValidationService(MachineValidationRepository machineValidationRepository) {
        this.machineValidationRepository = machineValidationRepository;
    }

    public boolean validateMachine(final int machineId) {
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
            LOGGER.info("Response status code: " + response.statusCode());
            LOGGER.info("Response body: " + response.body());
            // ASSUMING RETURNS 403 in case machine is not allowed.
            machineValidationRepository.save(new MachineValidation(LocalDateTime.now(), machineId, (response.statusCode() == 200) ? "OK" : "KO"));
            return (response.statusCode() == 200);
        } catch (Exception ex) {
            LOGGER.error("Error calling the validator", ex);
        }
        return Boolean.FALSE;
    }

    public boolean validateMachineMock(final int machineId, final boolean validation) {
        machineValidationRepository.save(new MachineValidation(LocalDateTime.now(), machineId, (validation) ? "OK" : "KO"));
        return validation;
    }

}
