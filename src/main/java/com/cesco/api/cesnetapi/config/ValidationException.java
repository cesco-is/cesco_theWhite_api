package com.cesco.api.cesnetapi.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import com.cesco.api.cesnetapi.cm.models.CesResponse;
import com.cesco.api.cesnetapi.cm.models.CesResponseHeader;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidationException {

    /**
     * GET 방식 Validation Handler
     * GET 방식의 경우 Valid로 체크되지 않아 해당 로직 구현.
     * @since   2021-07-28
     * @author  박성철
     * @version 2107.1
     */
    public ResponseEntity<CesResponse> checkValidation (BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
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

            CesResponse errResult = new CesResponse(new CesResponseHeader(errorStr), null);
            ResponseEntity<CesResponse> errResponseEntity = new ResponseEntity<CesResponse>(errResult, HttpStatus.BAD_REQUEST);
            return errResponseEntity;
        }
        else {
            return null;
        }
    }
}
