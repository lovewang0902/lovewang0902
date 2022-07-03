package com.mybatis.example.service;
/**
 * @author 89003422
 *
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybatis.example.dto.KafkaReaderDto;
import com.mybatis.example.utils.JsonUtil;
import com.sf.fvp.ConvertUtil;
import com.sf.fvp.dto.FactRouteDto;
import com.sf.kafka.check.util.AuthUtil;

import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;


public class KafkaReadService{
    private static ExecutorService executor;
//    private Logger logger;
    private static Logger logger;
    
    public KafkaReadService() {
        this.logger = LoggerFactory.getLogger(KafkaReadService.class);
    }
    
    public List<String> fetchData(final KafkaReaderDto dto) {
        final List<String> messageList = Collections.synchronizedList(new ArrayList<String>());
        try {
            final Map<String, Integer> brokers = getBrokerMap(AuthUtil.getBrokers(dto.getClusterName(), dto.getToken(), dto.getMonitorUrl()));
            logger.info("brokers :" +brokers);
            //获取分区数
            final int totalParition = this.getPartitionsForTopic(dto.getTopicName(), brokers);
          //使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            final List<Future<?>> futures = new ArrayList<Future<?>>(totalParition);
            for (int i = 0; i < totalParition; ++i) {
                final int partition = i;
              
              //将任务执行结果存储到List中
                futures.add(KafkaReadService.executor.submit(() -> this.fetchDataByParition(dto, brokers, messageList, partition)));
            }
            this.await(futures);
        }
        catch (Exception e) {
            this.logger.error("fetchData fail", (Throwable)e);
        }
        return messageList;
    }
    
    private void await(final List<Future<?>> futures) {
        futures.parallelStream().forEach(future -> {
            try {
                future.get(1L, TimeUnit.MINUTES);
            }
            catch (InterruptedException e) {
                future.cancel(true);
                this.logger.error(e.getMessage(), (Throwable)e);
            }
            catch (ExecutionException e2) {
                this.logger.error(e2.getMessage(), (Throwable)e2);
                future.cancel(true);
            }
            catch (TimeoutException e3) {
                future.cancel(true);
                this.logger.error("await fail", (Throwable)e3);
            }
            return;
        });
        futures.clear();
    }
    /**
     * 从分区中获取数据
     * */
    private void fetchDataByParition(final KafkaReaderDto dto, final Map<String, Integer> brokerMaps, final List<String> messageList, final int partition) {
        try {
            CustomKafkaConsumer.run(dto.getMaxSize(), dto.getTopicName(), partition, brokerMaps, new KafkaReaderCallbackService() {
                @Override
                public boolean callback(final byte[] msg, final long offset, final int parition) {
                    final String dealMsg = this.filter(dto.isDeserialize(), msg, offset, parition, dto.getFilterStr());
                    if (dealMsg != null) {
                        messageList.add(dealMsg);
                        return true;
                    }
                    return false;
                }
                
                private String filter(final boolean deSerialize, final byte[] data, final long offset, final int parition, final String filterStr) {
                    try {
                        String message;
                        if (deSerialize) {
                            message = JsonUtil.encode2json(ConvertUtil.fromByte(FactRouteDto.class, data));
                        }
                        else {
                            message = new String(data, "UTF-8");
                        }
                        if (StringUtils.isBlank((CharSequence)filterStr) || message.contains(filterStr)) {
                            return String.format("{\"offset\":%d, \"parition\":%d, \"message\":%s}", offset, parition, message);
                        }
                    }
                    catch (Exception e) {
                        KafkaReadService.this.logger.error("filter fail", (Throwable)e);
                    }
                    return null;
                }
            });
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
    }
    /**
     * 获取集群ip
     * */
    private static Map<String, Integer> getBrokerMap(final String brokers) {
        final Map<String, Integer> brokerMap = new HashMap<String, Integer>();
        for (final String broker : brokers.split(",")) {
            final String[] brokerArr = broker.split(":");
            brokerMap.put(brokerArr[0], Integer.parseInt(brokerArr[1]));
        }
        return brokerMap;
    }
    /**
     * 从主题中获取分区数
     * */
    private int getPartitionsForTopic(final String topic, final Map<String, Integer> brokerInfo) {
        final List<String> topics = Collections.singletonList(topic);
        final List<PartitionMetadata> partitionInfos = new ArrayList<PartitionMetadata>();
        for (final Map.Entry<String, Integer> entry : brokerInfo.entrySet()) {
        	//简单消费者
            SimpleConsumer consumer = null;
            try {
                consumer = new SimpleConsumer((String)entry.getKey(), (int)entry.getValue(), 20000, 131072, "partitionLookup");
                final TopicMetadataRequest req = new TopicMetadataRequest(topics);
                final TopicMetadataResponse resp = consumer.send(req);
                final List<TopicMetadata> metadata = (List<TopicMetadata>)resp.topicsMetadata();
                for (final TopicMetadata item : metadata) {
                    partitionInfos.addAll(item.partitionsMetadata());
                }
                break;
            }catch (Exception e) {
                this.logger.error(e.getMessage(), (Throwable)e);
            }
            finally {
                if (null != consumer) {
                    consumer.close();
                }
            }
        }
        return partitionInfos.size();
    }
    
    static {
        KafkaReadService.executor = Executors.newCachedThreadPool();
    }
}

