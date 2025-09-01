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
    private HttpClient httpClient;

    public MachineValidationService(MachineValidationRepository machineValidationRepository ) {
        this.machineValidationRepository = machineValidationRepository;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Validates machine based on a Id Check on an external API reached via REST.
     * I assumed it would be a POST message although it could be PUT or GET depending on what the endpoint does,
     *      would have to modify several things to make it work correctly.
     * On the challenge it doesn't specify the validity of a machine validation,
     *      assumed it was handled on demand, but I would add a check to see if there is a previous entry recently if there was.
     * Would recommend that this is added to the challenge Tbh.
     *
     * @param machineId ID of the machine
     * @return Boolean validation if the response is 200 OK or anything else Ko, assumed not valid machine would be 403.
     */
    public boolean validateMachine(final int machineId) {
        try {
            if(httpClient == null){
                this.httpClient = HttpClient.newHttpClient();
            }
            final String json = "{ \"machineId\":\"%s\"}";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://api.example.com/endpoint"))
                    // THIS SHOULD BE AN ENVIRONMENT VARIABLE in a Service for a Springboot app for example.
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest
                            .BodyPublishers
                            .ofString(String.format(json, machineId)))  // replace "post_data" with your data
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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

    /**
     * It's a Mock, saves to BD on demand. Exclusively to comply with the test text.
     * @param machineId
     * @param validation
     * @return
     */
    public boolean validateMachineMock(final int machineId, final boolean validation) {
        machineValidationRepository.save(new MachineValidation(LocalDateTime.now(), machineId, (validation) ? "OK" : "KO"));
        return validation;
    }

}
