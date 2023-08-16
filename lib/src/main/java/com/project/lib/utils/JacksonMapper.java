package com.project.lib.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class JacksonMapper {

	private ObjectMapper mapper = new ObjectMapper();

	@PostConstruct
	public void init() {
		this.mapper = new ObjectMapper();
	}


	public String objectToJsonString(Object object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}

	public Map<String,Object> jsonStringToMap(String jsonString) throws JsonProcessingException {
		return mapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
	}

    public Map<String,String> jsonStringToMapString(String jsonString) throws JsonProcessingException {
        return mapper.readValue(jsonString, new TypeReference<Map<String,String>>(){});
    }

	public List<Object> jsonStringToList(String jsonString) throws Exception {
		return mapper.readValue(jsonString, new TypeReference<List<Object>>(){});
	}

	public Map<String,Object> objectToMap(Object object) throws Exception {
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return mapper.readValue(mapper.writeValueAsString(object), new TypeReference<Map<String,Object>>(){});
	}

	public <T> T mapToClass(Map<String, Object> map, Class<T> t) throws JsonProcessingException {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
		//mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		return mapper.convertValue(map, mapper.getTypeFactory().constructType(t));
    }

}
