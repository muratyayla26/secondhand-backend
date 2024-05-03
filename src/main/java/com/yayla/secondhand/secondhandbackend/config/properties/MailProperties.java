package com.yayla.secondhand.secondhandbackend.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.yml")
public class MailProperties {
    @Value("${secondhand.app.feUrl}")
    private String feUrl;
    @Value("${secondhand.app.mailFromAddress}")
    private String mailFromAddress;
}
