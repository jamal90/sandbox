//import kafka.api.ConsumerMetadataRequest;
//import kafka.api.ConsumerMetadataResponse;
//import kafka.api.PartitionOffsetRequestInfo;
//import kafka.cluster.Broker;
//import kafka.common.OffsetMetadataAndError;
//import kafka.consumer.Consumer;
//import kafka.consumer.ConsumerConfig;
//import kafka.consumer.ConsumerIterator;
//import kafka.consumer.KafkaStream;
//import kafka.javaapi.OffsetFetchRequest;
//import kafka.javaapi.OffsetFetchResponse;
//import kafka.javaapi.OffsetRequest;
//import kafka.javaapi.PartitionMetadata;
//import kafka.common.TopicAndPartition;
//import kafka.javaapi.OffsetResponse;
//import kafka.javaapi.TopicMetadataRequest;
//import kafka.javaapi.TopicMetadataResponse;
//import kafka.javaapi.consumer.ConsumerConnector;
//import kafka.javaapi.consumer.SimpleConsumer;
//import kafka.message.MessageAndMetadata;
//import kafka.network.BlockingChannel;
//import kafka.serializer.StringDecoder;
//import kafka.utils.ZKGroupTopicDirs;
//import kafka.utils.ZkUtils;
//import org.I0Itec.zkclient.ZkClient;
//import org.I0Itec.zkclient.exception.ZkMarshallingError;
//import org.I0Itec.zkclient.serialize.SerializableSerializer;
//import org.I0Itec.zkclient.serialize.ZkSerializer;
//import org.junit.Test;
//import scala.Option;
//import scala.collection.JavaConversions;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
///**
// * Created by I076097 on 1/17/2017
// */
//public class KafkaAdminUtilsTest {
//
//    @Test
//    public void fetchOffsetLag(){
//
//        // steps
//        // 1. create a simple consumer for the topic
//
//        String brokerHost = "localhost";
//        int brokerPort = 9092;
//        String clientName = "offset-checker";
//        final List<String> topics = Collections.singletonList("DEFAULT_TENANT");
//        final SimpleConsumer offsetCheckerConsumer = new SimpleConsumer(brokerHost, brokerPort, Integer.MAX_VALUE, 32 * 1024, clientName);
//
//        // 2. get all the partitions for the topic
//        final TopicMetadataRequest topicMetadataRequest = new TopicMetadataRequest(topics);
//        final TopicMetadataResponse topicMetadataRes = offsetCheckerConsumer.send(topicMetadataRequest);
//        final List<PartitionMetadata> partitionMetadatas = topicMetadataRes.topicsMetadata().get(0).partitionsMetadata();
//
//        final List<Integer> partitionIds = partitionMetadatas.stream().mapToInt(PartitionMetadata::partitionId).boxed().collect(Collectors.toList());
//
//        // 3. check the offset committed against each partition for the consumer group, and total offsets
//        final Map<Integer, Long> default_tenant = getOffsetLagFromZk(offsetCheckerConsumer, "DEFAULT_TENANT", partitionIds, clientName);
//
//        System.out.println(default_tenant);
//        /* SOME INFO
//            log-size -> next offset for message in the partition
//            earliest-offset -> the first offset in the partition (for a relatively new partition, it's 0 always). when the messages has gone over the retention period, then the earlier offset would point to non-zero values
//            latest -> same as log-size
//            current offset -> offset by this consumer group for the partition
//         */
//
//    }
//
//    // to be used when kafka broker is used as consumer group coordinator for fetching the offsets committed
//    private static Map<Integer, Long> getOffsetLagFromKafkaCoordinator(SimpleConsumer consumer, String topic, List<Integer> partitionIds,
//                                                                       String clientName) {
//
//        final Map<Integer, Long> partitionToOffsetLag = new HashMap<>();
//        final List<TopicAndPartition> topicAndPartitions = partitionIds.stream().map((partitionId) -> new TopicAndPartition(topic, partitionId)).collect(Collectors.toList());
//
//        // get the offset co-ordinator
//        BlockingChannel channel = new BlockingChannel("localhost", 9092,
//                BlockingChannel.UseDefaultBufferSize(),
//                BlockingChannel.UseDefaultBufferSize(),
//                5000 /* read timeout in millis */);
//        channel.connect();
//        final String MY_GROUP = topic;
//
//        final ConsumerMetadataRequest consumerMetadataRequest = new ConsumerMetadataRequest("DEFAULT_TENANT", ConsumerMetadataRequest.CurrentVersion(), 0, clientName);
//        channel.send(consumerMetadataRequest);
//
//        final ConsumerMetadataResponse consumerMetadataResponse = ConsumerMetadataResponse.readFrom(channel.receive().buffer());
//        final Broker offsetCoordBroker = consumerMetadataResponse.coordinatorOpt().get();
//        channel.disconnect();
//
//        // get the current offset using offset coordinator manager
//        channel =  new BlockingChannel(offsetCoordBroker.host(), offsetCoordBroker.port(),
//                BlockingChannel.UseDefaultBufferSize(),
//                BlockingChannel.UseDefaultBufferSize(),
//                5000 /* read timeout in millis */);
//        channel.connect();
//
//
//        // fetch the current offset
//        final OffsetFetchRequest offsetFetchRequest = new OffsetFetchRequest(topic/* consumer-group */, topicAndPartitions, kafka.api.OffsetFetchRequest.CurrentVersion(), 0, clientName);
//
//        // final OffsetFetchResponse offsetFetchResponse = consumer.fetchOffsets(offsetFetchRequest);
//
//        channel.send(offsetFetchRequest.underlying());
//        final OffsetFetchResponse offsetFetchResponse = OffsetFetchResponse.readFrom(channel.receive().buffer());
//
//        // fetch the latest offset (log size)
//        Map<TopicAndPartition, PartitionOffsetRequestInfo> latestOffsetRequestMap = new HashMap<>();
//        topicAndPartitions.forEach(topicAndPartition -> {
//            latestOffsetRequestMap.put(topicAndPartition, new PartitionOffsetRequestInfo(kafka.api.OffsetRequest.LatestTime(), 1));
//        });
//
//        final OffsetRequest latestOffsetRequest = new OffsetRequest(latestOffsetRequestMap, kafka.api.OffsetRequest.CurrentVersion(), clientName);
//        final OffsetResponse latestOffsetResponse = consumer.getOffsetsBefore(latestOffsetRequest);
//
//
//        offsetFetchResponse.offsets().entrySet().forEach(kV -> {
//            final TopicAndPartition key = kV.getKey();
//            final OffsetMetadataAndError offsetMetadata = kV.getValue();
//            // TODO if (offsetMetadata.error()) // handle error here!
//            // TODO latestOffsetResponse.hasError()   //  to be handled
//            partitionToOffsetLag.put(key.partition(), latestOffsetResponse.offsets(key.topic(), key.partition())[0] - offsetMetadata.offset());
//        });
//
//        return partitionToOffsetLag;
//    }
//
//
//    private Map<Integer, Long> getOffsetLagFromZk(SimpleConsumer consumer, String topic, List<Integer> partitionIds, String clientName){
//
//        final Map<Integer, Long> partitionToOffsetLag = new HashMap<>();
//        final List<TopicAndPartition> topicAndPartitions = partitionIds.stream().map((partitionId) -> new TopicAndPartition(topic, partitionId)).collect(Collectors.toList());
//
//        Map<TopicAndPartition, PartitionOffsetRequestInfo> latestOffsetRequestMap = new HashMap<>();
//        topicAndPartitions.forEach(topicAndPartition -> {
//            latestOffsetRequestMap.put(topicAndPartition, new PartitionOffsetRequestInfo(kafka.api.OffsetRequest.LatestTime(), 1));
//        });
//
//        final OffsetRequest latestOffsetRequest = new OffsetRequest(latestOffsetRequestMap, kafka.api.OffsetRequest.CurrentVersion(), clientName);
//        final OffsetResponse latestOffsetResponse = consumer.getOffsetsBefore(latestOffsetRequest);
//
//        final Map<TopicAndPartition, Long> offsetsCommited = getOffsets("localhost:2181", "DEFAULT_TENANT", "DEFAULT_TENANT");
//
//        partitionIds.stream().forEach(partitionId -> {
//            partitionToOffsetLag.put(partitionId, latestOffsetResponse.offsets(topic, partitionId)[0] - offsetsCommited.get(new TopicAndPartition(topic, partitionId)));
//        });
//
//        return partitionToOffsetLag;
//
//    }
//
//
//    public static Map<TopicAndPartition,Long> getOffsets(String zkServers,
//                                                         String groupID,
//                                                         String topic) {
//        ZKGroupTopicDirs topicDirs = new ZKGroupTopicDirs(groupID, topic);
//        Map<TopicAndPartition,Long> offsets = new HashMap<>();
//
//            final ZkClient zkClient = new ZkClient(zkServers, 1000000, 1000000, new ZkSerializer() {
//                @Override
//                public byte[] serialize(Object o) throws ZkMarshallingError {
//                    return new byte[0];
//                }
//
//                @Override
//                public Object deserialize(byte[] bytes) throws ZkMarshallingError {
//                    return new String(bytes);
//                }
//            });
//            for (Integer partition : IntStream.range(0,20).boxed().collect(Collectors.toList())) {
//                String partitionOffsetPath = topicDirs.consumerOffsetDir() + "/" + partition;
//                try{
//                    Option<String> maybeOffset = ZkUtils.readDataMaybeNull(zkClient, partitionOffsetPath)._1();
//                    Long offset = maybeOffset.isDefined() ? Long.parseLong(maybeOffset.get()) : null;
//                    TopicAndPartition topicAndPartition =
//                            new TopicAndPartition(topic, Integer.parseInt(partition.toString()));
//                    offsets.put(topicAndPartition, offset);
//                }catch (ZkMarshallingError zkMarshallingError){
//                    zkMarshallingError.printStackTrace();
//                }
//
//
//            }
//        return offsets;
//    }
//
//    @Test
//    public void startKafkaConsumer() throws InterruptedException {
//        Properties consumerProp = new Properties();
//        consumerProp.put("zookeeper.connect", "localhost:2181");
//        consumerProp.put("group.id", "DEFAULT_TENANT");
//        consumerProp.put("auto.commit.enable", "true");
//        consumerProp.put("zookeeper.sync.time.ms", "200");
//
//        final ConsumerConfig consumerConfig = new ConsumerConfig(consumerProp);
//        final ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
//        Map<String, Integer> topicMap = new HashMap<>();
//        topicMap.put("DEFAULT_TENANT", 1);
//
//        final Map<String, List<KafkaStream<String, String>>> messageStreamMap = consumerConnector.createMessageStreams(topicMap, new StringDecoder(null), new StringDecoder(null));
//        final List<KafkaStream<String, String>> msgStreams = messageStreamMap.get("DEFAULT_TENANT");
//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//
//        for (KafkaStream<String, String> kafkaStream : msgStreams){
//            final ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
//            executorService.submit(new KafkaConsumerThread(kafkaStream));
//        }
//
//        countDownLatch.await();
//
//    }
//
//    static class KafkaConsumerThread implements Runnable {
//
//        private final KafkaStream<String, String> kafkaStream ;
//
//        public KafkaConsumerThread(KafkaStream<String, String> kafkaStream) {
//            this.kafkaStream = kafkaStream;
//        }
//
//        @Override
//        public void run() {
//            final ConsumerIterator<String, String> messageItr = kafkaStream.iterator();
//
//            while(messageItr.hasNext()){
//                try {
//                    Thread.sleep(Long.MAX_VALUE);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                final MessageAndMetadata<String, String> msgAndMetadta = messageItr.next();
//                System.out.println("Consumed Message: " +  msgAndMetadta.message());
//            }
//
//        }
//    }
//}
