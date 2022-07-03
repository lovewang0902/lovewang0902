package com.mybatis.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mybatis.example.domain.WaybillNo;

public interface WaybillMapper {
	/*
	 * 查询所以学生
	 */
	List<WaybillNo> findAllWaybillNo();

	/*
	 * 通过id查询学生
	 */
	WaybillNo selectWaybillNo(int id);

	/*
	 * 通过学生名，模糊查询学生
	 */
	List<WaybillNo> selectWaybillNoByParams(@Param("params") Map<String, Object> params);

	/*
	 * 保存运单信息
	 */
	int saveWaybillNo(WaybillNo waybillNo);

	
}
