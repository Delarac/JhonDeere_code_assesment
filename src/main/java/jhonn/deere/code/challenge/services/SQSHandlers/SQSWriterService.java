package jhonn.deere.code.challenge.services.SQSHandlers;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.io.IOException;

@Service
public class SQSWriterService extends SQSBaseHandler {

    public SQSWriterService() throws IOException {
        super(Boolean.FALSE);
    }

    /**
     * sends the message to the SQS2.
     *
     * @param messageBody
     */
    public void sendMessage(final String messageBody) {
        final SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .delaySeconds(5)
                .build();
        // HERE WE COULD ADD A SERVICE TO HANDLE THE RESPONSE OF SENDING THE SECOND TIME FOR A OK++
        sqsClient.sendMessage(sendMsgRequest);
    }

    public void sendMessageMock(final String messageBody) {
        // NOT DOING ANYTHING.
    }

}
