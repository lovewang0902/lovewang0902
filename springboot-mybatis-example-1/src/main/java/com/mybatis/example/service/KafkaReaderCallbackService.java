/**
 * 
 */
/**
 * @author 89003422
 *
 */
package com.mybatis.example.service;

public interface KafkaReaderCallbackService
{
    boolean callback(final byte[] p0, final long p1, final int p2);
}