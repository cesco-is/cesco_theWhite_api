package com.cesco.api.cesnetapi.sample.controllers;

import com.cesco.api.cesnetapi.cm.models.CesResponse;
import com.cesco.api.cesnetapi.cm.models.CesResponseHeader;
import com.cesco.api.cesnetapi.config.ApiException;
import com.cesco.api.cesnetapi.sample.services.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * TestController
 * 
 * API 실행부분을 코딩
 * @since   2021-07-26
 * @author  조선호
 * @version 2107.1
 */
@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 서버 날짜 리턴
     * @return  서버 날짜
     * @see     TestService
     */
    @GetMapping("/dbinfo")
    @ResponseBody
    public ResponseEntity<CesResponse> getServerDateTime() throws Exception {

        CesResponse rc = new CesResponse(new CesResponseHeader(), testService.getServerTime());
        return new ResponseEntity<CesResponse>(rc, HttpStatus.OK);
    }

    @GetMapping("/dbinfo/{parameter}")
    @ResponseBody
    public ResponseEntity<CesResponse> getServerDateTimeProc(@PathVariable(value = "parameter") String parameter) throws Exception {

        if (parameter.equals("error")) {

            // ApiException 을 발생시켜 사용자에게 전달한다.
            throw new ApiException(HttpStatus.BAD_REQUEST, "ApiException 에러에요~!" + parameter);
        } else if (parameter.equals("error2")) {
            
            // 다른 에러는 이렇게 처리 한다.
            throw new Exception("Exception 에러에요~!" + parameter);
        } else if (parameter.equals("error3")) {

            int test = 10 / 0;
            log.info("{}", test);
        }

        // 리턴할 데이터 정리만...
        CesResponse rc = new CesResponse(new CesResponseHeader(), testService.getServerTime(parameter));
        log.debug(rc.toString());

        return new ResponseEntity<>(rc, HttpStatus.ACCEPTED);
    }
}
