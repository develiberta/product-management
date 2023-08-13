package com.project.order.service;

import com.project.order.search.SpecBuilder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseService {
	protected final Logger logger = LoggerFactory.getLogger(BaseService.class);
	
	@Autowired
	protected SpecBuilder specBuilder;

	@Autowired
	protected ModelMapper modelMapper;

}
