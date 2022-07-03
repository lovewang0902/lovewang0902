package com.mybatis.example.utils;


import java.util.List;

import javax.sound.midi.Track;


import com.alibaba.fastjson.JSON;
import com.mybatis.example.dto.KafkaReaderDto;
import com.mybatis.example.service.KafkaReadService;
import com.sf.kafka.api.consume.ConsumeConfig;
import com.sf.kafka.api.consume.IByteArrayMessageConsumeListener;
import com.sf.kafka.api.consume.IStringMessageConsumeListener;
import com.sf.kafka.api.consume.KafkaConsumeRetryException;
import com.sf.kafka.api.consume.KafkaConsumerRegister;
import com.sf.kafka.api.produce.IKafkaProducer;
import com.sf.kafka.api.produce.ProduceConfig;
import com.sf.kafka.api.produce.ProducerPool;
import com.sf.kafka.exception.KafkaException;



/**
 * 
 * @Title: KafkaProducerUtil
 * @Description: kafka消息生产者
 * @Version:1.0.0
 * @author pancm
 * @date 2018年4月2日
 */
public final class KafkaProducerUtil {

	/**
	 * 生产者
	 */
	public static void KafkaProducerData() {
		
		String url = "http://mommon-other2.intsit.sfdc.com.cn:1080/mom-mon/monitor/requestService.pub";
		String topic = "AIR_AIS_CORE_SEND_AND_GET";
		String message = "{testName:202201140930}";
		// 主题所在的集群名称
		String clusterName = "bus";
		// 航班起飞
		String topicTokens = "AIR_AIS_CORE_SEND_AND_GET" + ":" + "$dZR0sWv";
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "32");
		ProduceConfig config = new ProduceConfig(5, url, clusterName, topicTokens);
		IKafkaProducer kafkaProducer = new ProducerPool(config);
		// 发送数据
		kafkaProducer.sendString(topic, message);
		System.err.println(kafkaProducer);
	}

	/**
	 * 消费者
	 */
	public static void consumeKafkaData() {

		String url = "http://mommon.sit.sf-express.com/mom-mon/monitor/requestService.pub";
		String topic = "GTS_FULL_TRACK";
		// 主题所在的集群名称
		String clusterName = "sit-ibugtscore";
		// 航班起飞
		String systemIdToken = "GTS_FULL_TRACK:*96axKv^";
/*		String url = "http://mommon-other2.intsit.sfdc.com.cn:1080/mom-mon/monitor/requestService.pub";
		String topic = "AIR_AIS_CORE_SEND_AND_GET";
		// 主题所在的集群名称
		String clusterName = "bus";
		// 航班起飞
		String systemIdToken = "AIR_AIS_CORE_SEND_AND_GET:$dZR0sWv";
*/
		int threadCount = 10;
		
		ConsumeConfig config = new ConsumeConfig(systemIdToken, url, clusterName, topic, threadCount);

		IStringMessageConsumeListener<Track> consumeListener = new IStringMessageConsumeListener<Track>() {

			@Override
			public Track decode(String bytesData) {
				return JSON.parseObject(new String(bytesData), Track.class);
			}

			@Override
			public void onMessage(List<Track> datas) throws KafkaConsumeRetryException {
				for (Track track : datas) {
					System.out.println(track);
				}

			}

		};
		
		try {// boolean registerByteArrayConsumer =
			KafkaConsumerRegister.registerStringConsumer(config, consumeListener);

		} catch (KafkaException e) {
			e.printStackTrace();
		}
		
		
	}

	public static void filterMsg() {
		
		String url = "http://mommon-other2.intsit.sfdc.com.cn:1080/mom-mon/monitor/requestService.pub";
		String topic = "AIR_AIS_CORE_SEND_AND_GET";
		// 主题所在的集群名称
		String clusterName = "bus";
		// 航班起飞
		String topicTokens = "AIR_AIS_CORE_SEND_AND_GET" + ":" + "$dZR0sWv";
		String filterText = "testName";
		String readNumText = "100";
		
		List<String> fetchData = null;
		KafkaReaderDto dto = new KafkaReaderDto();
		dto.setMonitorUrl(url);
		dto.setClusterName(clusterName);
		dto.setTopicName(topic);
		dto.setFilterStr(filterText);
		dto.setMaxSize(Integer.valueOf(readNumText));
		dto.setToken(topicTokens);
		KafkaReadService kafkaReadService = new KafkaReadService();
		fetchData = kafkaReadService.fetchData(dto);
		System.err.println("筛选到的数据为：" + fetchData.toString());
	}
	public static void main(String[] args) {
		// 消费数据
		consumeKafkaData();
//		filterMsg();
		

	}

}
