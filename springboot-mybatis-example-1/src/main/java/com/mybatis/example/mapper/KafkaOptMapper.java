package com.mybatis.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mybatis.example.domain.KafkaOpt;

public interface KafkaOptMapper {
	/*
	 * 查询全部
	 */
	List<KafkaOpt> findAllKafkaConfig();

	/*
	 * 通过id查询
	 */
	KafkaOpt selectKafkaConfig(int id);

	/*
	 * 按参数，模糊查询
	 */
	List<KafkaOpt> selectKafkaConfigByParams(@Param("params") Map<String, Object> params);

	/*
	 * 保存
	 */
	int saveKafkaConfig(KafkaOpt kafkaOpt);
	
	/*
	 *更新
	 * */
	int updateKafkaOpt(KafkaOpt kafkaOpt);

	
}
