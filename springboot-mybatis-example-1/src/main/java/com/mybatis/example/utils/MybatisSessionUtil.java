package com.mybatis.example.utils;

import java.io.IOException;

import org.apache.ibatis.io.Resources;

import org.apache.ibatis.session.SqlSession;

import org.apache.ibatis.session.SqlSessionFactory;

import org.apache.ibatis.session.SqlSessionFactoryBuilder;



/*public class MybatisSessionUtil {

    static SqlSessionFactory sqlSessionFactory = null;
 
    static{
 
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
 
        try {
 
            sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
 
        } catch (IOException e) {
 
            e.printStackTrace();
 
        }
 
    }
 

    public static SqlSession getSession(){
 
        return sqlSessionFactory.openSession();
 
    }

} */