package github.heyxhh.httpclient;

import org.junit.Test;

import github.heyxhh.httpclient.GetRequest;

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
