/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.github.pig.sso;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * @author lengleng
 * @date 2018年01月27日13:00:09
 * 单点登录客户端
 */
@EnableOAuth2Sso
@SpringBootApplication
public class PigSsoClientDemoApplication {


    public static void main(String[] args) {


        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36");
        headers.add("Accept-Encoding", "gzip,deflate");
        headers.add("Accept-Language", "zh-CN");
        headers.add("Connection", "Keep-Alive");
        String code = "lion:lion";
        String auth = "Basic "+PigSsoClientDemoApplication.encode(code);
        headers.add("Authorization", auth);
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap();
        paramMap.add("username",  "admin");
        paramMap.add("password",  "$2a$10$vg5QNHhCknAqevx9vM2s5esllJEzF/pa8VZXtFYHhhOhUcCw/GWyS");
        paramMap.add("randomStr", "19201544884633838");
        paramMap.add("code","1111");
        paramMap.add("grant_type","password");
        paramMap.add("scope","server");
        String url =  "http://127.0.0.1:56513/bus/refresh";
//        String url = "http://127.0.0.1:9999/auth/oauth/token";

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity(paramMap,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.postForEntity(url, httpEntity, String.class);
        System.out.println( result);

//        SpringApplication.run(PigSsoClientDemoApplication.class, args);

    }


    /**
     * 编码
     *
     * @param content
     * @return
     */
    public static String encode(String content) {
        return new sun.misc.BASE64Encoder().encode(content.getBytes());
    }

    /**
     * 解码
     *
     * @param source
     * @return
     */
    public static String decode(String source) {
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            return new String(decoder.decodeBuffer(source));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
