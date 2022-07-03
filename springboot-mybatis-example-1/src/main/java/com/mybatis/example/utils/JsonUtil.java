/**
 * @author 89003422
 *
 */
package com.mybatis.example.utils;


import com.fasterxml.jackson.databind.*;
import com.mysql.cj.log.LogFactory;

import lombok.extern.log4j.Log4j2;

import com.fasterxml.jackson.core.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.*;

public class JsonUtil
{
    private static final Logger logger = LoggerFactory.getLogger("info");
    private static ObjectMapper objectMapper;
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private JsonUtil() {
    }
    
    public static <T> T decode(final String json, final Class<?> valueType) {
        try {
            return (T) JsonUtil.objectMapper.readValue(json, (Class)valueType);
        }
        catch (Exception e) {
            JsonUtil.logger.error("json parse decode error:{}", (Throwable)e);
            return null;
        }
    }
    
    public static String encode2json(final Object object) {
        try {
            return JsonUtil.objectMapper.writeValueAsString(object);
        }
        catch (Exception e) {
            JsonUtil.logger.error("json parse encode error:{}", (Throwable)e);
            return null;
        }
    }
    
    private static ObjectMapper initObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.setDateFormat((DateFormat)new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return objectMapper;
    }
    
    static {
//        logger = LoggerFactory.getLogger("info");
        JsonUtil.objectMapper = initObjectMapper();
    }
}
