package com.mybatis.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybatis.example.domain.KafkaOpt;
import com.mybatis.example.mapper.KafkaOptMapper;

@Service
public class KafkaOptService{

	@Autowired
    private KafkaOptMapper kafkaOptMapper;
	
	public List<KafkaOpt> findAllKafkaConfig() {
		return kafkaOptMapper.findAllKafkaConfig();
	}
	public KafkaOpt selectKafkaConfig(int id) {
        return kafkaOptMapper.selectKafkaConfig(id);
    }
	public List<KafkaOpt> selectKafkaConfigByParams(Map<String, Object> params) {
		return kafkaOptMapper.selectKafkaConfigByParams(params);
	}
	public int saveKafkaConfig(KafkaOpt kafkaOpt) {
		return kafkaOptMapper.saveKafkaConfig(kafkaOpt);
	}
	public int updateKafkaOpt(KafkaOpt kafkaOpt) {
		return kafkaOptMapper.updateKafkaOpt(kafkaOpt);
	}
	
}