package com.makao.test.main;

import static org.junit.Assert.*;
import net.sf.json.JSONObject;

import org.junit.After;
import org.junit.Test;

import com.makao.test.utils.ClearDBUtil;
import com.makao.test.utils.HttpUtils;

/**
 * @description: TODO
 * @author makao
 * @date 2016年5月8日
 * 1.清空数据库
	2.增加supervisor
	3.增加city
	4.增加area(会建对应的product表)
	5.增加vendor
	6.增加product
 */
public class NewProductTest {

	@Test
	public void test() {
		JSONObject result = null;
		String newsupervisor = "http://localhost:8080/supervisor/new";
		String supervisor = "{\"userName\":\"darou\",\"password\":\"test\"}";
		
		String newcity = "http://localhost:8080/city/new/1";
		String city = "{\"cityName\":\"上海\"}";
		
		String newarea = "http://localhost:8080/area/new/1";
		String area = "{\"areaName\":\"张江\",\"cityName\":\"上海\",\"catalogs\":\"水果=0,食材=1,零食=2,省钱=3\",\"cityId\":1}";
		
		String newvendor = "http://localhost:8080/vendor/new/1";
		String vendor = "{\"userName\":\"马靠\",\"areaId\":1,\"cityId\":1,\"cityName\":\"上海\",\"areaName\":\"张江\"}";
	

		String newprodcut = "http://localhost:8080/product/vnew/1";
		String product = "{\"productName\":\"海南千禧小番茄\",\"catalog\":\"水果\",\"label\":\"无标签\",\"price\":\"12.00\",\"standard\":\"一份足2斤\",\"marketPrice\":\"30.00\",\"inventory\":12,\"sequence\":3,\"status\":\"库存紧张\",\"origin\":\"海南\",\"isShow\":\"yes\",\"showWay\":\"s\",\"salesVolume\":7637,\"likes\":3972,\"areaId\":1,\"cityId\":1}";
	
		result = HttpUtils.doPostStr(newsupervisor,supervisor);
		assertEquals("增加supervisor成功",result.get("msg"));
		result = HttpUtils.doPostStr(newcity,city);
		assertEquals("200",result.get("msg"));
		result = HttpUtils.doPostStr(newarea,area);
		assertEquals("200",result.get("msg"));
		result = HttpUtils.doPostStr(newvendor,vendor);
		assertEquals("200",result.get("msg"));
		result = HttpUtils.doPostStr(newprodcut,product);
		assertEquals("200",result.get("msg"));
	}
	
//	@After
//	public void tearDown() throws Exception {
//		ClearDBUtil.dropDBTables("wxmall");;
//	}
}
