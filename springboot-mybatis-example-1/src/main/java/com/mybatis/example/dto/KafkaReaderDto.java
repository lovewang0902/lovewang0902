package com.mybatis.example.dto;
/**
 * @author 89003422
 *
 */
public class KafkaReaderDto
{
    private String clusterName;
    private String monitorUrl;
    private String topicName;
    private int maxSize;
    private String filterStr;
    private String token;
    private boolean deserialize;
    
    public String getTopicName() {
        return this.topicName;
    }
    
    public void setTopicName(final String topicName) {
        this.topicName = topicName;
    }
    
    public int getMaxSize() {
        return this.maxSize;
    }
    
    public void setMaxSize(final int maxSize) {
        this.maxSize = maxSize;
    }
    
    public String getFilterStr() {
        return this.filterStr;
    }
    
    public void setFilterStr(final String filterStr) {
        this.filterStr = filterStr;
    }
    
    public boolean isDeserialize() {
        return this.deserialize;
    }
    
    public void setDeserialize(final boolean deserialize) {
        this.deserialize = deserialize;
    }
    
    public String getClusterName() {
        return this.clusterName;
    }
    
    public void setClusterName(final String clusterName) {
        this.clusterName = clusterName;
    }
    
    public String getMonitorUrl() {
        return this.monitorUrl;
    }
    
    public void setMonitorUrl(final String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public void setToken(final String token) {
        this.token = token;
    }
}
