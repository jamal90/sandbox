import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Region;
import org.junit.BeforeClass;
import org.junit.Test;

public class S3Test {

    public static AmazonS3 s3Client;

    @BeforeClass
    public static void setupS3() {
        final ClientConfiguration clientConfiguration = new ClientConfiguration();
        String accessKey = System.getEnv("access_key");
        String accessSecret = System.getEnv("access_secret");
        s3Client = new AmazonS3Client(new BasicAWSCredentials(accessKey, accessSecret), clientConfiguration);
        s3Client.setRegion(Region.US_Standard.toAWSRegion());
        s3Client.setEndpoint("s3.amazonaws.com");
    }

    @Test
    public void testS3WithDemilter() {

        final ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName("com.jam.testfilerepo");
        listObjectsRequest.setMarker("Files/Things/12345/");

        listObjectsRequest.setPrefix("Files/Things/12345/");
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setMaxKeys(3);

        final ObjectListing objectListing = s3Client.listObjects(listObjectsRequest);

        System.out.println("Common Prefixes:");
        objectListing.getCommonPrefixes().forEach(s -> {
            System.out.println(s);
        });

        System.out.println("Keys: ");
        objectListing.getObjectSummaries().forEach(objectSummary -> {
            System.out.println(objectSummary.getKey());
        });

        System.out.println("Is Truncated: " + objectListing.isTruncated());
        System.out.println("Next Key: " + objectListing.getNextMarker());

        // fetching the next set of items for the same dir listing

        final ListObjectsRequest listObjectsRequest1 = new ListObjectsRequest();
        listObjectsRequest1.setBucketName("com.jam.testfilerepo");
        listObjectsRequest1.setMarker("Files/Things/12345/dir1/");

        listObjectsRequest1.setPrefix("Files/Things/12345/");
        listObjectsRequest1.setDelimiter("/");
        listObjectsRequest1.setMaxKeys(3);

        final ObjectListing objectListing1 = s3Client.listObjects(listObjectsRequest1);

        System.out.println("Common Prefixes:");
        objectListing1.getCommonPrefixes().forEach(s -> {
            System.out.println(s);
        });

        System.out.println("Keys: ");
        objectListing1.getObjectSummaries().forEach(objectSummary -> {
            System.out.println(objectSummary.getKey());
        });

        System.out.println("Is Truncated: " + objectListing1.isTruncated());
        System.out.println("Next Key: " + objectListing1.getNextMarker());

    }


}
