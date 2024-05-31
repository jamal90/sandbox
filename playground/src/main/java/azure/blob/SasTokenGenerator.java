package azure.blob;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.SharedAccessBlobPermissions;
import com.microsoft.azure.storage.blob.SharedAccessBlobPolicy;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;

// generate SAS Token based on stored access policy
public class SasTokenGenerator {

	public static void main(String[] args) throws StorageException, InvalidKeyException, URISyntaxException {

//		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString().buildClient();

		CloudStorageAccount storageAccount = CloudStorageAccount.parse("DefaultEndpointsProtocol=https;AccountName=dd4ab372a18c49be9e2c;AccountKey=yTyLeouIkPrzXxJ1z1UyA7kH/XrJzN64LUvv9YMevPxAtOxwvzFMh6Q4WmfvjpOKgtkeXPn2HV1cPU4m82ocoA==;EndpointSuffix=core.windows.net");

		// create the stored access policy
		SharedAccessBlobPolicy sharedAccessBlobPolicy = new SharedAccessBlobPolicy();
		sharedAccessBlobPolicy.setPermissions(EnumSet.of(SharedAccessBlobPermissions.READ, SharedAccessBlobPermissions.LIST));
		sharedAccessBlobPolicy.setSharedAccessStartTime(Date.from(Instant.now().minus(15, ChronoUnit.MINUTES)));
		sharedAccessBlobPolicy.setSharedAccessExpiryTime(Date.from(Instant.parse("9999-12-31T23:59:59.000Z")));

		BlobContainerPermissions blobContainerPermissions = new BlobContainerPermissions();
		blobContainerPermissions.getSharedAccessPolicies().put("policy-spark-read", sharedAccessBlobPolicy);

		// blob service client
		CloudBlobClient cloudBlobClient = storageAccount.createCloudBlobClient();

		// create container
		CloudBlobContainer cloudBlobContainer = cloudBlobClient.getContainerReference("cold-store-export");

		// uploading the stored access policies
		cloudBlobContainer.uploadPermissions(blobContainerPermissions);

		// generating SAS token based on stored access policy
		String sasContainer = cloudBlobContainer.generateSharedAccessSignature(null, "policy-spark-read");
		System.out.println(sasContainer);

	}

	// sig=ZweEWupt2GFDiPJK8BOlIc1IpNBGB12j7RAhmyHJyCU%3D&sv=2018-03-28&si=policy-spark-read&sr=c
}