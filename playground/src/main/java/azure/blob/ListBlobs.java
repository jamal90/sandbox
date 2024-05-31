package azure.blob;

import com.microsoft.azure.storage.StorageException;

import com.microsoft.azure.storage.blob.CloudBlobContainer;

import com.microsoft.azure.storage.blob.ListBlobItem;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class ListBlobs {
	public static void main(String[] args) throws URISyntaxException, StorageException {
	String storageAccName = "stcoldexport5zxx3u5ks4v3";
	String storageContainerName = "cold-store-export";
	String containerSasToken = "sv=2019-07-07&sr=c&si=export-policy&sig=Pa8DTqP9D1RPxiPGxqMImdKHNVu7C4xBMHuwks0cYQ0%3D";
	String containerUrlWithSas = String.format("https://%s.blob.core.windows.net/%s/?%s", storageAccName, storageContainerName, containerSasToken);
	CloudBlobContainer cloudBlobContainer = new CloudBlobContainer(new URI(containerUrlWithSas));

	Iterable<ListBlobItem> listBlobItems = cloudBlobContainer.listBlobs("/exports/466B7EB757574413826756D9B7FE63C4/");
	listBlobItems.forEach(blob -> {
		System.out.println(blob.getUri());
	});

		cloudBlobContainer.listBlobsSegmented().getLength();
}}



