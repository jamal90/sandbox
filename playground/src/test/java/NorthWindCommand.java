//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.netflix.hystrix.HystrixCommand;
//import com.netflix.hystrix.HystrixCommandGroupKey;
//import com.netflix.hystrix.HystrixCommandProperties;
//import org.asynchttpclient.AsyncHttpClient;
//import org.asynchttpclient.DefaultAsyncHttpClient;
//import org.asynchttpclient.DefaultAsyncHttpClientConfig;
//import org.asynchttpclient.ListenableFuture;
//import org.asynchttpclient.Request;
//import org.asynchttpclient.Response;
//
///**
// * Created by I076097 on 11/21/2016
// */
//public class NorthWindCommand extends HystrixCommand<JsonObject> {
//
//    private final AsyncHttpClient asyncHttpClient;
//
//    public NorthWindCommand() {
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("NorthWind-IoT"))
//                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(3000)));
//        asyncHttpClient = new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().build());
//    }
//
//    @Override
//    protected JsonObject run() throws Exception {
//        final Request req = asyncHttpClient.prepareGet("http://services.odata.org/V4/Northwind/Northwind.svc/Products(1)?$format=json").build();
//        final ListenableFuture<Response> responseListenableFuture = asyncHttpClient.executeRequest(req);
//        return new Gson().fromJson(responseListenableFuture.get().getResponseBody(), JsonObject.class);
//    }
//
//    @Override
//    protected JsonObject getFallback() {
//        final JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("IS_SUCCESS", false);
//        return jsonObject;
//    }
//}
