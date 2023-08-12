package com.project.order.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBean {
	@Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        
        //mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.getConfiguration().setAmbiguityIgnored(true);
        
        return mapper;
    }
}
