package github.heyxhh.httpclient;

import org.junit.Test;

import github.heyxhh.httpclient.PostRequest;

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
