package com.mybatis.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybatis.example.domain.WaybillNo;
import com.mybatis.example.mapper.WaybillMapper;

@Service
public class WaybillService{

	@Autowired
    private WaybillMapper waybillMapper;
	
	public WaybillNo selectWaybillNo(int id) {
        return waybillMapper.selectWaybillNo(id);
    }
	public List<WaybillNo> findAllWaybillNo() {
		return waybillMapper.findAllWaybillNo();
	}
	public List<WaybillNo> selectWaybillNoByParams(Map<String, Object> params) {
		return waybillMapper.selectWaybillNoByParams(params);
	}
	public int saveWaybillNo(WaybillNo waybillNo) {
		return waybillMapper.saveWaybillNo(waybillNo);
	}
	
}