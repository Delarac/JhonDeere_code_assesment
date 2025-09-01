package jhonn.deere.code.challenge;

import org.apache.log4j.Logger;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


public class SQSHandler {
    protected static final String FIRST_DATASET = "{sessionGuid: \"a65de8c4-6385-4008-be36-5df0c5104fd5\",sequenceNumber: 1,machineId: 1,data: [{type: \"distance\",unit: \"m\",value: \"100\"},{type: \"workedSurface\",unit: \"m2\",value: \"600\"}]}";
    protected static final String SECOND_DATASET = "{sessionGuid: \"a65de8c4-6385-4008-be36-5df0c5104fd5\",sequenceNumber: 2,machineId: 1,data: [{type: \"distance\",unit: \"m\",value: \"102\"},{type: \"workedSurface\",unit: \"m2\",value: \"610\"}]}";

    protected String accessKeyId;
    protected String secretAccessKey;
    protected Region region;
    protected SqsClient sqsClient;
    protected String queueUrl;
    protected static final Logger logger = Logger.getLogger(SQSHandler.class);

    public SQSHandler(final boolean reader) throws IOException {
        // THIS WHOLE THING SHOULD BE DEFINED WITH PROPER CREDENTIALS, and TESTED.
        final InputStream input =
                SQSHandler.class.getClassLoader().getResourceAsStream(reader ? "aws-credentials-sqs1.properties" : "aws-credentials-sqs2.properties");
        Properties prop = new Properties();
        try {
            prop.load(input);
            this.accessKeyId = prop.getProperty("AWS_ACCESS_KEY_ID");
            this.secretAccessKey = prop.getProperty("AWS_SECRET_ACCESS_KEY");
            this.region = Region.of(prop.getProperty("AWS_REGION_ID"));
            this.sqsClient = SqsClient.builder().region(region).build();
            this.queueUrl = prop.getProperty("AWS_QUEUE_DEFINITION");
        } catch (Exception e) {
            logger.error("ERROR CREATING SQS CONECTION");
            throw e;
        }
    }



}
