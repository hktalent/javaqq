package com.blee.service;

import java.io.IOException;

import com.blee.http.RequestContext;
import com.blee.http.ResponseContext;

public interface HttpService {
    
    ResponseContext execute(RequestContext request) throws IOException;

    public abstract void destroy();

}
