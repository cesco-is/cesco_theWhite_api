package com.cesco.api.cesnetapi.config;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.cesco.api.cesnetapi.cm.models.CesResponse;
import com.cesco.api.cesnetapi.cm.models.CesResponseHeader;
import com.cesco.api.cesnetapi.funtions.StringFunctions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 전역 에러 핸들러
 * 
 * ApiException 으로 처리 되지 않은 에러는 500 에러로 처리 한다.
 * @since   2021-07-26
 * @author  조선호
 * @version 2107.1
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * 그외 모든 에러 핸들러
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        
        printErrorMsg(request, ex, true);

        // jennifer test
        try {
            throw ex;
        } catch (Exception e) { }

        return new ResponseEntity<>(new CesResponse(new CesResponseHeader("알 수 없는 에러가 발생하였습니다."), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 사용자 정의 에러 핸들러
     * @param ex ApiException
     * @param request
     * @return
     */
    @ExceptionHandler({ ApiException.class })
    public ResponseEntity<Object> handleApiException(ApiException ex, WebRequest request) {
        
        printErrorMsg(request, ex, false);

        // jennifer test
        try {
            throw ex;
        } catch (Exception e) { }

        return new ResponseEntity<>(new CesResponse(new CesResponseHeader(ex.getMessage()), null), ex.getHttpStatus());
    }

    /**
     * Validation 에러 핸들러
     * @param ex MethodArgumentNotValidException
     * @param request
     * @return
     */
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleValidException(MethodArgumentNotValidException ex, WebRequest request) {
        
        printErrorMsg(request, ex, false);

        // jennifer test
        try {
            throw ex;
        } catch (Exception e) { }

        List<String> errors = ex.getBindingResult() // 에러들
                         .getFieldErrors()
                         .stream()
                         .map(x -> String.format("%s(%s)", x.getDefaultMessage(), x.getField()))
                         .collect(Collectors.toList());        
        
        CesResponseHeader header = new CesResponseHeader(StringFunctions.listToString(errors));
        return new ResponseEntity<Object>(new CesResponse(header, null), HttpStatus.BAD_REQUEST);
    }

    /**
     * 에러 메시지 logger 처리
     * @param request 요청
     * @param t 에러
     */
    private void printErrorMsg(WebRequest request, Throwable t, Boolean isError) {
        
        HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        String transactionId = request.getHeader("tran-id");
        String className = t.getClass().getSimpleName();
        String throwableMsg = t.getMessage();
        String requestURI = "(NONE)";

        // http method 정보
        if (httpServletRequest != null) {
            requestURI = String.format("(%s)%s", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());   
        }

        // 메시지 처리
        String errorMessage = String.format("%s => (%s)%s (tid : %s)", requestURI, className, throwableMsg, transactionId);

        if (isError) {
            log.error(errorMessage, t);
        } else {
            log.warn(errorMessage, t);
        }
    }
}
