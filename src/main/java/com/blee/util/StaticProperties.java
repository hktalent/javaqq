package com.blee.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个用于加载配置文件的类
 * @author blee
 *
 */
public class StaticProperties {
    
    private static final String default_properties_dir = "/home/blee/temp/"; 
    private static final String properties_file_name = "javaqq.properties";
    
    private static Logger log = LoggerFactory.getLogger(StaticProperties.class);

    private static Properties properties;

    private static volatile StaticProperties cloud;

    private StaticProperties() {
        init();
    }

    public String loadString(String name) {
        return properties.getProperty(name);
    }

    public boolean loadBoolean(String name) {
        boolean result = false;
        if (properties.containsKey(name)) {
            try {
                result = Boolean.parseBoolean(properties.getProperty(name));
            } catch (Exception e) {
            }
        }
        return result;
    }

    public int loadInt(String name) {
        int result = Integer.MAX_VALUE;
        if (properties.containsKey(name)) {
            try {
                result = Integer.parseInt(properties.getProperty(name));
            } catch (Exception e) {
            }
        }
        return result;
    }

    public long loadLong(String name) {
        return loadLong(name, Long.MAX_VALUE);
    }

    public long loadLong(String name, long defaultValue) {
        long result = defaultValue;
        if (properties.containsKey(name)) {
            try {
                result = Long.parseLong(properties.getProperty(name));
            } catch (Exception e) {
            }
        }
        return result;
    }    
    
    public float loadFloat(String key) {
        return loadFloat(key, -1f);
    }
    
    public float loadFloat(String string, float f) {
        float result = f;
        if(properties.containsKey(string)) {
            try {
                result = Float.parseFloat(properties.getProperty(string));
            } catch(Exception e) {}
        }
        return result;
    }

    public boolean hasProperty(String name) {
        return properties.containsKey(name);
    }

    public static StaticProperties instance() {
        if (cloud == null) {
            synchronized (StaticProperties.class) {
                if (cloud == null) {
//########## CLOUDSOFTLICENSECHECK ##########                    
                    cloud = new StaticProperties();
                }
            }
        }
        return cloud;
    }

    private void init() {
        File propertiesFile = new File(default_properties_dir + properties_file_name);
        if(!propertiesFile.exists()) {
            URL url = StaticProperties.class.getClassLoader().getResource(properties_file_name);
            propertiesFile = new File(url.getFile());
        }
        log.info("将要初始化配置文件,配置文件路径为[{}]", propertiesFile.getAbsolutePath());
        properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFile));
            log.info("配置项总个数[{}]", properties.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
