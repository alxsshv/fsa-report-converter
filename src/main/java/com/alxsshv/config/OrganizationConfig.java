package com.alxsshv.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "organization")
public class OrganizationConfig {
    private String sign;
    private String title;
}
