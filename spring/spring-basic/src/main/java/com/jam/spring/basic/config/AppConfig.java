package com.jam.spring.basic.config;

import com.jam.spring.basic.datasource.DummyDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:datasource.properties")
@EnableConfigurationProperties()
public class AppConfig {

    @Bean
    public DummyDataSource dummyDataSource(@Value("${dds.username}") String username,
                                           @Value("${dds.password}") String password,
                                           @Value("${dds.url}") String url) {
        DummyDataSource dummyDataSource = new DummyDataSource();
        dummyDataSource.setPwd(password);
        dummyDataSource.setUrl(url);
        dummyDataSource.setUsername(username);

        return dummyDataSource;
    }

}
