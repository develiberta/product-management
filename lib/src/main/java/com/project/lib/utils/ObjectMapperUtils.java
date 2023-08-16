package com.project.lib.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* */
public class ObjectMapperUtils {
    private static ModelMapper modelMapper;

    /* */
    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    /**
     * Hide from public usage.
     */
    private ObjectMapperUtils() {
    }

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     */
    public static <S, D> D map(final S entity, Class<D> outClass) {
    	if (entity == null) return null;
    	
        return modelMapper.map(entity, outClass);
    }

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     */
    public static <S, D> List<D> mapAll(final Collection<S> entityList, Class<D> outCLass) {
    	List<D> list = new ArrayList<D>();
    	if (entityList == null || entityList.size() == 0) return list;
   
    	for(S entity : entityList) {
    		list.add(modelMapper.map(entity, outCLass));
    	}
    	
    	return list;
    }

    /**
     * Maps {@code source} to {@code destination}.
     */
    public static <S, D> D map(final S source, D destination) {
    	if (source == null || destination == null) return null;
    	
        modelMapper.map(source, destination);
        return destination;
    }
}
