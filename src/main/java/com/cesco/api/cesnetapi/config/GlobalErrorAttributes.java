package com.cesco.api.cesnetapi.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> error = super.getErrorAttributes(webRequest, options);

        if (error.get("error") != null) {
            error.put("respMsg", error.get("error").toString());
        }

        error.put("params", webRequest.getParameterMap());

        result.put("header", error);
        result.put("body", null);
        return result;
    }
}
