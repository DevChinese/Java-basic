package com.hua.httpclient;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PostRequest {

    /**
     * @description:执行无参数的POST请求，并设置Header来伪装浏览器请求
     */
    public static void doPostNoParam() {
        // 创建httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建post请求实例
        HttpPost httpPost = new HttpPost("https://www.oschina.net/");
        // 伪装成浏览器请求
        httpPost.setHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");

        CloseableHttpResponse response = null;

        try {
            // 执行请求
            response = httpClient.execute(httpPost);

            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                // 内容写入文件
                FileUtils.writeStringToFile(new File("./oschina.html"), content, "UTF-8");
                System.out.println("内容长度："+content.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description: 执行带参数的POST请求
     * 模拟开源中国检索java，并伪装浏览器请求，输出响应结果为html文件
     */
    public static void doPostWithParam() {
        // 创建httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建post请求实例
        HttpPost httpPost = new HttpPost("https://www.oschina.net/search");

        // 伪装成浏览器请求
        httpPost.setHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");

        // 设置2个post参数，一个是scope、一个是q
        List<NameValuePair> params =  new ArrayList<NameValuePair>(0);
        params.add(new BasicNameValuePair("scope", "project"));
        params.add(new BasicNameValuePair("q", "java"));

        CloseableHttpResponse response = null;
        try {
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);


            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                // 内容写入文件
                FileUtils.writeStringToFile(new File("./oschina-param.html"), content, "UTF-8");
                System.out.println("内容长度："+content.length());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }

                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void doPostWithJsonParam() {
        // 创建httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建post请求实例
        HttpPost httpPost = new HttpPost("https://www.oschina.net/search");

        // 伪装成浏览器请求
        httpPost.setHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");

        // 设置传输格式为json
        httpPost.setHeader("Content-Type", "application/json");

        String jsonParam = new String("{'scope': 'project', 'q': 'Java'}");

        // 创建StringEntity实例
        StringEntity stringEntity = new StringEntity(jsonParam, "UTF-8");
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                // 内容写入文件
                FileUtils.writeStringToFile(new File("./oschina-json-param.html"), content, "UTF-8");
                System.out.println("内容长度："+content.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }

                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }
}
