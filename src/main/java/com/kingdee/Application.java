package com.kingdee;

import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.ds.druid.DruidDSFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zxq
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.kingdee.dao")
public class Application {

    public static void main(String[] args) {
        DSFactory.setCurrentDSFactory(new DruidDSFactory());
        SpringApplication.run(Application.class, args);
    }

}
