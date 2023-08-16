package com.project.product.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound=true),
})
@Data
public class ProductAppConfig {
	@Value("${product-management.order.server:'http://localhost:8081'}")
	private String orderServer;
}
