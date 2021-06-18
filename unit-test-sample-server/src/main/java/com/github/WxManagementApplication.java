package com.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author langer_peng
 */
@SpringBootApplication
@EnableJpaRepositories("com.github.langerpeng.repo")
@ConfigurationPropertiesScan
public class WxManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxManagementApplication.class, args);
    }
}
