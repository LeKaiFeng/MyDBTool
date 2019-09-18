/**
 * Copyright (C),2019-09-04,Galaxis
 * Date:2019/9/4 0004 10:12
 * Author:LELE
 * Description:
 */

package galaxis.lee.utils;

import galaxis.lee.models.DatabaseConfig;
import javafx.scene.control.Control;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBConfigUtil {

    /**
     * 将页面配置数据  写入配置文件
     */

    public static void configFile(DatabaseConfig config) {
        clearConfig("DBConfig.properties");
        Properties pop = new Properties();
        pop.setProperty("url", "jdbc:mysql://" + config.getHost() + ":" + config.getPort() + "/" + config.getSchema() + "?serverTimezone=CTT&useUnicode=true&characterEncoding=" + config.getEncoding() + "&allowMultiQueries=true");
        pop.setProperty("username", config.getUsername());
        pop.setProperty("password", config.getPassword());
        pop.setProperty("driver", "com.mysql.jdbc.Driver");
        try {
            pop.store(new FileOutputStream("DBConfig.properties"), "数据库基本配置");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearConfig(String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置文件
     *
     * @return
     */
    public static Map<String, String> getConf() {
        Map<String, String> map = new HashMap<String, String>();
        File file = new File("DBConfig.properties");
        //如果配置文件不存在
        if(!file.exists()){
            try {
                file.createNewFile();
                Properties pop = new Properties();
                pop.setProperty("url", "jdbc:mysql://127.0.0.1:3306/galaxis?serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true");
                pop.setProperty("username", "root");
                pop.setProperty("password", "admin");
                pop.setProperty("driver", "com.mysql.jdbc.Driver");
                try {
                    pop.store(new FileOutputStream("DBConfig.properties"), "数据库默认配置");
                    System.out.println("数据库配置文件不存在，已生成默认配置");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println("配置文件创建失败");

            }
        }
        // boolean canUse = false;
        // try {
        //     canUse = file.exists() || file.createNewFile();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // if (!canUse) {
        //     System.out.println("操作配置文件失败， 无法继续");
        //     return null;
        // }


        try (InputStream is = new FileInputStream(file)) {
            Properties pop = new Properties();
            pop.load(is);
            //如果是个空的配置文件  添加默认配置
            if(StringUtils.isAnyEmpty(pop.getProperty("url"),pop.getProperty("username"),pop.getProperty("password"),pop.getProperty("driver"))){
                System.out.println("数据库配置存在数据为null,已全部重置为默认数据");
                pop.setProperty("url", "jdbc:mysql://127.0.0.1:3306/galaxis?serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true");
                pop.setProperty("username", "root");
                pop.setProperty("password", "admin");
                pop.setProperty("driver", "com.mysql.jdbc.Driver");
                try {
                    pop.store(new FileOutputStream("DBConfig.properties"), "数据库默认配置");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            map.put("url", pop.getProperty("url"));
            map.put("username", pop.getProperty("username"));
            map.put("password", pop.getProperty("password"));
            map.put("driver", pop.getProperty("driver"));
            return map;

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        /*for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }*/
        return null;
    }
}
