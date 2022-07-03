/**
 * 
 */
/**
 * @author 89003422
 *
 */
package com.mybatis.example.service;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.OffsetRequest;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;

public class CustomKafkaConsumer{
    private static final Logger logger;
    
    public static void run(final long maxReads, final String a_topic, final int a_partition, final Map<String, Integer> a_seedBrokers, final KafkaReaderCallbackService reader) {
        final PartitionMetadata metadata = findLeader(a_seedBrokers, a_topic, a_partition);
        final String leadBroker = metadata.leader().host();
        final int port = metadata.leader().port();
        final String clientName = "Client_" + a_topic + "_" + a_partition;
        final SimpleConsumer consumer = new SimpleConsumer(leadBroker, port, 100000, 65536, clientName);
        final long earliest = getOffsetBy(consumer, a_topic, a_partition, OffsetRequest.EarliestTime(), clientName);
        final long lastOffset = getOffsetBy(consumer, a_topic, a_partition, OffsetRequest.LatestTime(), clientName);
        long readOffset = Math.max(lastOffset - maxReads, earliest);
        long count = lastOffset - readOffset;
    Label_0284:
        while (count > 0L) {
            final FetchRequest req = new FetchRequestBuilder().clientId(clientName).addFetch(a_topic, a_partition, readOffset, 100000).build();
            final FetchResponse fetchResponse = consumer.fetch(req);
            if (fetchResponse.hasError()) {
                break;
            }
            for (final MessageAndOffset messageAndOffset : fetchResponse.messageSet(a_topic, a_partition)) {
                final long currentOffset = messageAndOffset.offset();
                final ByteBuffer payload = messageAndOffset.message().payload();
                final byte[] bytes = new byte[payload.limit()];
                payload.get(bytes);
                readOffset = messageAndOffset.nextOffset();
                reader.callback(bytes, currentOffset, a_partition);
                --count;
                if (count < 0L) {
                    break Label_0284;
                }
            }
        }
        consumer.close();
    }
    
    public static long getOffsetBy(final SimpleConsumer consumer, final String topic, final int partition, final long whichTime, final String clientName) {
        final TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        final Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
        final kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest((Map<TopicAndPartition, PartitionOffsetRequestInfo>)requestInfo, OffsetRequest.CurrentVersion(), clientName);
        final OffsetResponse response = consumer.getOffsetsBefore(request);
        if (response.hasError()) {
            return 0L;
        }
        final long[] offsets = response.offsets(topic, partition);
        return offsets[0];
    }
    
    private static PartitionMetadata findLeader(final Map<String, Integer> a_seedBrokers, final String a_topic, final int a_partition) {
        PartitionMetadata returnMetaData = null;
    Label_0317:
        for (final Map.Entry<String, Integer> entry : a_seedBrokers.entrySet()) {
            SimpleConsumer consumer = null;
            try {
                consumer = new SimpleConsumer((String)entry.getKey(), (int)entry.getValue(), 100000, 65536, "leaderLookup");
                final List<String> topics = Collections.singletonList(a_topic);
                final TopicMetadataRequest req = new TopicMetadataRequest((List<String>)topics);
                final TopicMetadataResponse resp = consumer.send(req);
                final List<TopicMetadata> metaData = (List<TopicMetadata>)resp.topicsMetadata();
                for (final TopicMetadata item : metaData) {
                    for (final PartitionMetadata part : item.partitionsMetadata()) {
                        if (part.partitionId() == a_partition) {
                            returnMetaData = part;
                            break Label_0317;
                        }
                    }
                }
            }
            catch (Exception e) {
                CustomKafkaConsumer.logger.error("Error communicating with Broker [" + entry.getKey() + "] to find Leader for [" + a_topic + ", " + a_partition + "] Reason: " + e);
            }
            finally {
                if (consumer != null) {
                    consumer.close();
                }
            }
        }
        return returnMetaData;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
