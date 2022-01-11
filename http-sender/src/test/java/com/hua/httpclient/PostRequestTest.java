package com.hua.httpclient;

import org.junit.Test;

public class PostRequestTest {
    @Test
    public void testPostNoParam() {
        PostRequest.doPostNoParam();
    }

    @Test
    public void testPostWithParam() {
        PostRequest.doPostWithParam();
    }

    @Test
    public void testPostWithJsonParam() {
        PostRequest.doPostWithJsonParam();
    }
}
