package com.project.order.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource(value="classpath:application.properties", ignoreResourceNotFound=true),
})
@Data
public class OrderAppConfig {
	@Value("${product-management.product.server:'http://localhost:8082'}")
	private String productServer;
}
