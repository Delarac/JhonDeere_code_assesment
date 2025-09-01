package jhonn.deere.code.challenge;

import jhonn.deere.code.challenge.entities.MachineValidation;
import jhonn.deere.code.challenge.repositories.MachineValidationRepository;
import jhonn.deere.code.challenge.services.MachineValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MachineValidatorTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @Mock
    private MachineValidationRepository machineValidationRepository;

    @Test
    public void testValidateMachine_shouldReturnTrue_whenResponseCodeIs200() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(200);
        final MachineValidationService machineValidator = new MachineValidationService(machineValidationRepository);
        machineValidator.setHttpClient(httpClient);
        boolean result = machineValidator.validateMachine(12345);
        assertTrue(result);
        verify(httpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
        verify(machineValidationRepository).save(any(MachineValidation.class));
    }

    @Test
    public void testValidateMachine_shouldReturnFalse_whenResponseCodeIsNot200() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(403);
        final MachineValidationService machineValidator = new MachineValidationService(machineValidationRepository);
        machineValidator.setHttpClient(httpClient);
        boolean result = machineValidator.validateMachine(12345);
        assertFalse(result);
        verify(httpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
        verify(machineValidationRepository).save(any(MachineValidation.class));
    }

    @Test
    public void testValidateMachine_shouldReturnFalse_whenExceptionIsThrown() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new RuntimeException());
        final MachineValidationService machineValidator = new MachineValidationService(machineValidationRepository);
        machineValidator.setHttpClient(httpClient);
        boolean result = machineValidator.validateMachine(12345);
        assertFalse(result);
        verify(httpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }
}