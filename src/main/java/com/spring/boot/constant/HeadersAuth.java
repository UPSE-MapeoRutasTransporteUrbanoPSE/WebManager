package com.spring.boot.constant;



import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Base64;

public class HeadersAuth {

    
    public HttpHeaders createHttpHeaders(String user, String password)
    {
        String notEncoded = user + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + encodedAuth);
        return headers;
    }

    
    
    public MultiValueMap<String, String> headers() {
    	MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();	        
        headers = createHttpHeaders(DeclaracionVariable.USER, DeclaracionVariable.PASS);  
        return headers;
    }
    
}
