package jhonn.deere.code.challenge;

import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class SQSReader extends SQSHandler {
    public SQSReader() throws IOException {
        super(Boolean.TRUE);
    }

    public List<Message> recoverMessages() {
        final ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl).build();
        final ReceiveMessageResponse response = sqsClient.receiveMessage(receiveRequest);
        for (Message message : response.messages()) {
            logger.info(message.body());
            final DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();

            sqsClient.deleteMessage(deleteRequest);
        }
        return response.messages();
    }

    public List<Message> recoverMessagesMock() {
        final Message message1 = Message.builder()
                .messageId(String.valueOf(UUID.randomUUID()))
                .body(FIRST_DATASET)
                .build();
        final Message message2 = Message.builder()
                .messageId(String.valueOf(UUID.randomUUID()))
                .body(SECOND_DATASET)
                .build();

        return List.of(message1, message2);
    }


}
