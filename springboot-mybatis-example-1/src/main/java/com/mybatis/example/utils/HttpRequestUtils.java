package com.mybatis.example.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 发送post请求
 * @param sendUrl 要发送请求到的http地址
 * @param params 要附带的请求的参数
 * @param headers 要设置的请求头
 * @param getDataCharset 最后获取的返回的内容的字符集，默认为utf-8
 * @return
 */
/**
 * http 请求工具类
 */
@Slf4j
public final class HttpRequestUtils {
    //设置字符编码
    private static final String CHARSET = "UTF-8";

    private static RequestConfig defaultRequestConfig = RequestConfig
        .custom()
        //设置等待数据超时时间
        .setSocketTimeout(30000)
        //设置连接超时时间
        .setConnectTimeout(30000)
        //设置从连接池获取连接的等待超时时间
        .setConnectionRequestTimeout(30000)
        //.setStaleConnectionCheckEnabled(true)
        .build();

    //释放资源，httpResponse为响应流，httpClient为请求客户端
    private static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }

    //get请求带参数、带请求头
    public static String get(String urlWithParams, Map<String, String> header) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(urlWithParams);
        if (!MapUtils.isEmpty(header)) {
            header.forEach(httpget::addHeader);
        }
        CloseableHttpResponse response = httpClient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, CHARSET);
        httpget.releaseConnection();
        release(response, httpClient);
        return result;
    }

    public static String get(String urlWithParams) throws IOException {
        return get(urlWithParams, null);
    }

    //发送post请求，带json请求体和请求头
    public static String postJson(String url, String json, Map<String, String> headersMap) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            headers.add(entry.getKey(),entry.getValue());
        }
        org.springframework.http.HttpEntity<String> request = new org.springframework.http.HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response.getBody();
    }
	public static void main(String[] args) {
		
		Map<String,String> headers=new HashMap<>();
		headers.put("Context-Type", "application/json;charset=utf-8");
//		String s=sendGet("https://www.baidu.com/", headers, "utf-8");
		String sendUrl = "http://";
		String params = "{\r\n" + 
				"  \"requestId\": \"@now(yyyyMMddHHmmssSS)\",\r\n" + 
				"  \"systemCode\": \"CORE\",\r\n" + 
				"  \"apCode\": \"\",\r\n" + 
				"  \"generateNum\": 2,\r\n" + 
				"  \"operateDeptCode\": \"00\",\r\n" + 
				"  \"operateEmpCode\": \"0002\",\r\n" + 
				"  \"operateTm\": \"@now(yyyy-MM-dd HH:mm:ss)\"\r\n" + 
				"}";
		String s= postJson(sendUrl, params, headers);
		JSONObject jsonObject = JSON.parseObject(s);
		String action = jsonObject.getString("obj");
		System.out.println(action);
		
		//GET 方法
//		String geturl = "http://";
//		try {
//			String string = get(geturl);
//			System.err.println(string);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
