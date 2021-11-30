package com.hyj.netty.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static Properties property;

    static {
        property = new Properties();
        InputStream in = Config.class.getClassLoader().getResourceAsStream("config/config.properties");
        try {
            property.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key){
        return property.getProperty(key);
    }


    public static void main(String[] args) {
        System.out.println(Config.get("ip"));
    }

}
