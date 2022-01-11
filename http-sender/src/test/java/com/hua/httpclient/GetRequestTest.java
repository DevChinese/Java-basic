package com.hua.httpclient;

import org.junit.Test;

public class GetRequestTest {
    @Test
    public void testNoArgGet() {
        GetRequest.doGetNoParam("http://www.baidu.com");
    }

    @Test
    public void testWithParamGet() {
        GetRequest.doGetWithParam();
    }
}
