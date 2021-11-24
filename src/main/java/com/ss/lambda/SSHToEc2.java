package com.ss.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SSHToEc2 implements RequestHandler<String, Boolean> {
    @Override
    public Boolean handleRequest(String s, Context context) {
        return null;
    }
}
