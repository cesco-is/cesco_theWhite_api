package com.cesco.api.cesnetapi.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

/**
 * 파라미터 체크
 * @author 
 * @since 
 */
public class ParamValidCheck {
    
    /**
     * 파라미터 체크
     *  - ?
     *  - ?
     * @param bindingResult BindingResult
     */
    public static void checkValid(BindingResult bindingResult) throws ApiException {

        if (bindingResult.hasErrors()) {
            
            List<String> errors = bindingResult
                         .getFieldErrors()
                         .stream()
                         .map(x -> x.getDefaultMessage())
                         .collect(Collectors.toList());
            String errorStr = "";
            for(String str : errors){
                errorStr += str + ", ";
            }
            if(errorStr.length() >= 2){
                errorStr = errorStr.substring(0, errorStr.length() - 2);
            }

            throw new ApiException(HttpStatus.BAD_REQUEST, errorStr);
        }
    }
}
