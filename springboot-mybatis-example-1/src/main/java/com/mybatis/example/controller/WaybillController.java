package com.mybatis.example.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mybatis.example.domain.WaybillNo;
import com.mybatis.example.service.WaybillService;
import com.mybatis.example.utils.HttpRequestUtils;

@RestController
@RequestMapping("/WaybillNo")
public class WaybillController {
	
	private static Logger log = LoggerFactory.getLogger(WaybillController.class);
	@Autowired
	private WaybillService waybillService;

	/*
	 * 生成运单号
	 */
	@RequestMapping("/createWaybill")
	public String createWaybill(@RequestParam Map<String, Object> params) {
		
		String region = params.get("region").toString();
		String number = params.get("number").toString();
		String label = params.get("label").toString();
		String format = params.get("format").toString();
		String waybillNoList = null;
		
		Integer num = Integer.valueOf(number);
		// 如果是15位运单，调接口，参数：个数，标签入库
		if (region.equals("15")) {
			System.out.println("15位订单参数...." + params.toString());
			log.info("15位运单参数，出参params：{0}"+params.toString());//打印info级别的日志
			
			if(num <= 200) {
				Map<String, String> headers = new HashMap<>();
				headers.put("Context-Type", "application/json;charset=utf-8");
//				String s=sendGet("https://www.baidu.com/", headers, "utf-8");
				String sendUrl = "http://";
				Date date = new Date();
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String operateTm = dateformat.format(date);
				String requestId = UUID.randomUUID().toString();
				String waybillParams = "{\r\n" + "  \"requestId\": \"" + requestId + "\",\r\n"
						+ "  \"systemCode\": \"CORE\",\r\n"
						+ "  \"apCode\": \"-\",\r\n" + "  \"generateNum\": " + number
						+ ",\r\n" + "  \"\": \"aa\",\r\n" + "  \"bbb\": \"000\",\r\n"
						+ "  \"operateTm\": \"" + operateTm + "\"\r\n" + "}";
				String result = HttpRequestUtils.postJson(sendUrl, waybillParams, headers);
				JSONObject jsonObject = JSON.parseObject(result);
				waybillNoList = jsonObject.getString("obj");
				waybillNoList = waybillNoList.replaceAll("\"", "").replace("[", "").replace("]", "");
				System.out.println(waybillNoList);
				
				WaybillNo waybillNo = new WaybillNo();
				waybillNo.setWaybillNo(waybillNoList);
				waybillNo.setCount(num);
				waybillNo.setWaybillFormat(format);
				waybillNo.setLabel(label);
				waybillNo.setCreateTime(new Date());
				//入库
				waybillService.saveWaybillNo(waybillNo);
			}
			
		} else {

			// 否则不调接口，根据运单格式，个数，标签入库，生成运单号
			System.out.println("创建订单参数...." + params.toString());
		}
		
		return waybillNoList;

	}

	/*
	 * 查询所有用户
	 */
	@RequestMapping("/findAllWaybillNo")
	public List<WaybillNo> findAllWaybill() {
		List<WaybillNo> findAllWaybill = waybillService.findAllWaybillNo();
		System.out.println("查询所有用户的数据...."+findAllWaybill);
		return findAllWaybill;

	}
	/*
	 * 根据id查询用户
	 */
	@RequestMapping("/showWaybillNo/{id}")
	public String selectStudent(@PathVariable int id) {
		return waybillService.selectWaybillNo(id).toString();

	}

	/*
	 * 按运单号模糊查询
	 */
	@RequestMapping("/WaybillNoByParams")
	public List<WaybillNo> selectWaybillNoByNmae(@RequestParam Map<String, Object> params) {
		return waybillService.selectWaybillNoByParams(params);

	}

}