package com.jam.spring.basic;

import com.jam.spring.basic.datasource.DummyDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBasicApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBasicApplication.class, args);

        DummyDataSource dummyDataSource = (DummyDataSource) applicationContext.getBean("dummyDataSource");
        System.out.println("PWD - " + dummyDataSource.getPwd());
    }

}
