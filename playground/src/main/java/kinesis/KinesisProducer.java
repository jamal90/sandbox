package kinesis;


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClientBuilder;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.kinesis.common.KinesisClientUtil;

import java.time.Instant;
import java.util.concurrent.ExecutionException;

public class KinesisProducer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        String accessKey = System.getEnv("access_key");
        String accessSecret = System.getEnv("access_secret");
        AwsCredentialsProvider awsCredProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, accessSecret));
        Region region = Region.of("eu-central-1");
        KinesisAsyncClientBuilder kinesisClientBuilder = KinesisAsyncClient.builder()
                .credentialsProvider(awsCredProvider)
                .region(region);

        KinesisAsyncClient kinesisAsyncClient = KinesisClientUtil.createKinesisAsyncClient(kinesisClientBuilder);

        String message = "{" +
                "  \"type\": {" +
                "    \"propertySetType\": \"iot:pst1\"," +
                "    \"schema\": {" +
                "      \"prop1\": \"Numeric\"," +
                "      \"prop2\": \"String\"," +
                "      \"prop3\": \"Numeric\"," +
                "      \"prop4\": \"Numeric\"" +
                "    }" +
                "  }," +
                "  \"measure\": {" +
                "    \"thingId\": \"T1\"," +
                "    \"thingType\": \"ABC1\"," +
                "    \"namedPropertySet\": \"NPST1\"," +
                "    \"values\": [" +
                "      [" +
                "        " + Instant.now().toEpochMilli() + "," +
                "        10," +
                "        \"hello\"," +
                "        20," +
                "        10" +
                "      ]," +
                "      [" +
                "        " + (Instant.now().toEpochMilli() + 1 )+ "," +
                "        10," +
                "        \"hello\"," +
                "        20," +
                "        10" +
                "      ]," +
                "      [" +
                "        1641945800000," +
                "        10," +
                "        \"hello\"," +
                "        20," +
                "        10" +
                "      ]" +
                "    ]" +
                "  }" +
                "}";

        PutRecordRequest kinesisRecord = PutRecordRequest.builder()
                .partitionKey("T1/ABC/NPS1")
                .streamName("test-kafka-connect")
                .data(SdkBytes.fromByteArray(message.getBytes()))
                .build();

        kinesisAsyncClient.putRecord(kinesisRecord).get();

        System.out.println("done");
    }
}
