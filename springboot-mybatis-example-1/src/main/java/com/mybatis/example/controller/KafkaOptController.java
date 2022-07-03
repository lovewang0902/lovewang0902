package com.mybatis.example.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mybatis.example.domain.KafkaOpt;
import com.mybatis.example.service.KafkaOptService;

@RestController
@RequestMapping("/kafka")
public class KafkaOptController {

	private static Logger log = LoggerFactory.getLogger(WaybillController.class);
	@Autowired
	private KafkaOptService kafkaOptService;

	/*
	 * 新增配置
	 */
	@RequestMapping(value = "/saveKafkaConfig",method = RequestMethod.POST)
	public Map<String, Object> saveKafkaConfig(@RequestParam String kafkaConfigData) {
		log.info("保存参数 ",kafkaConfigData);
	
		
		KafkaOpt kafkaOpt = JSON.parseObject(kafkaConfigData,KafkaOpt.class);
//		Integer num = Integer.valueOf(number);
		kafkaOpt.setCreator("System");
		kafkaOpt.setCreateTime(new Date());

		// 入库
		int savaStatus = kafkaOptService.saveKafkaConfig(kafkaOpt);
		Map<String, Object> map = new HashMap<>();

		if (savaStatus == 1) {
			map.put("success", true);
			map.put("msg", "保存成功");
			log.info("保存到DB成功");
			return map;
		}
		map.put("success", false);
		map.put("msg", "保存失败");
		log.info("保存到DB失败");

		return map;

	}

	/*
	 * 查询全部
	 */
	@RequestMapping("/findAllKafkaConfig")
	public List<KafkaOpt> findAllKafkaConfig() {
		List<KafkaOpt> findAllWaybill = kafkaOptService.findAllKafkaConfig();
		System.out.println("查询全量数据...." + findAllWaybill);
		return findAllWaybill;

	}
	// 更新
	@RequestMapping(value="/updateKafkaInfo",method = RequestMethod.POST)
	public Map<String, Object> updateKafkaInfo(@RequestParam String kafkaConfigData) {
		
		KafkaOpt kafkaOpt = JSON.parseObject(kafkaConfigData,KafkaOpt.class);
		kafkaOpt.setUpdateTime(new Date());
		kafkaOpt.setCreator("System");
		log.info("<<<<<< 按id更新  >>>>>>");
		int updateStatus = kafkaOptService.updateKafkaOpt(kafkaOpt);
		Map<String, Object> map = new HashMap<String, Object>();
		if(updateStatus == 1) {
				map.put("success", true);
				map.put("msg", "更新成功！");
				return map;
		}
		System.err.println("返回false");
		map.put("success", false);
		map.put("msg", "更新文章失败，请稍后再试！");
		return map;
		}
	/*
	 * 按参数模糊查询
	 */
	@RequestMapping("/kafkaByParams")
	public List<KafkaOpt> selectKafkaByNmae(@RequestParam Map<String, Object> params) {
		return kafkaOptService.selectKafkaConfigByParams(params);

	}

	/*
	 * 根据id查询
	 */
	@RequestMapping("/showWaybillNo/{id}")
	public String selectStudent(@PathVariable int id) {
		return kafkaOptService.selectKafkaConfig(id).toString();

	}

}