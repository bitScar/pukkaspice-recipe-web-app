package com.pukkaspice.web.common.jersey.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

public class CustomConverter implements Converter<List<Map<String, Object>>, List<String>> {

    @SuppressWarnings("unchecked")
    public List<String> convert(Map<String, Object> value) {
    	
        Object values = value.get("values");
        if (values == null || !(values instanceof List)) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<String>();
        for (Object item : (List<Object>) values) {
            Map<String, Object> mapItem = (Map<String, Object>) item;
            Map<String, Object> skillMap = (Map<String, Object>) mapItem.get("skill");
            if (skillMap == null) {
                continue;
            }

            result.add(skillMap.get("name").toString());
        }

        return result;
    }

    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructMapLikeType(Map.class, String.class, Object.class);
    }

    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructCollectionLikeType(List.class, String.class);
    }

	public List<String> convert(List<Map<String, Object>> value) {
    	
    	
		return new ArrayList<String>();
//        Object values = value.get("values");
//        if (values == null || !(values instanceof List)) {
//            return Collections.emptyList();
//        }
//
//        List<String> result = new ArrayList<String>();
//        for (Object item : (List<Object>) values) {
//            Map<String, Object> mapItem = (Map<String, Object>) item;
//            Map<String, Object> skillMap = (Map<String, Object>) mapItem.get("skill");
//            if (skillMap == null) {
//                continue;
//            }
//
//            result.add(skillMap.get("name").toString());
//        }

//        return result;
	}
}