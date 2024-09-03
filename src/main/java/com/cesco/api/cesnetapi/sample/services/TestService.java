package com.cesco.api.cesnetapi.sample.services;

import com.cesco.api.cesnetapi.sample.mappers.TestMapper;
import com.cesco.api.cesnetapi.sample.models.ServerInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    
    @Autowired
    private TestMapper mapper;

    public ServerInfo getServerTime() throws Exception {

        return mapper.getServerDateTime();
    }

    public ServerInfo getServerTime(String parameter) throws Exception {

        return mapper.getServerDateTime2(parameter);
    }
}
