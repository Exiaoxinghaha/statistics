package com.xzdt.statistics;

import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {

    private static Properties properties = new Properties();

    static {
        System.out.println("loading settings file......");
        InputStream in = ConfigurationManager
                .class
                .getClassLoader()
                .getResourceAsStream("application.properties");

        try {

            properties.load(in);
            Thread.sleep(1500);
            System.out.println("successful！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // String 类型参数
    public static String getStringProp(String key){
        return checkValue(key);
    }

    // Integer 类型参数
    public static Integer getIntegerProp(String key){
        Integer formatValue = null;
        String value = checkValue(key);
        try{
            formatValue = Integer.valueOf(value);
        } catch (Exception e){
            e.printStackTrace();
        }

        return formatValue;
    }

    // Long 类型参数
    public static Long getLongProp(String key){

        Long formatValue = null;
        String value = checkValue(key);
        try{
            formatValue = Long.valueOf(value);
        } catch (Exception e){
            e.printStackTrace();
        }

        return formatValue;
    }

    // Boolean 类型参数
    public static Boolean getBooleanProp(String key){
        Boolean formatValue = null;
        String value = checkValue(key);
        try{
            formatValue = Boolean.valueOf(value);
        } catch (Exception e){
            e.printStackTrace();
        }

        return formatValue;
    }

    private static String checkValue(String key){
        String value = properties.getProperty(key);
        try {
            if(value ==null || value.isEmpty()){
                throw new Exception(key + "miss required value，please check your Constants Interface or Settings File");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

}
