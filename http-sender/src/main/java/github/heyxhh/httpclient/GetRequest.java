package github.heyxhh.httpclient;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GetRequest {

    public static void doGetNoParam(String urlPath){
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建get请求
        HttpGet httpGet = new HttpGet(urlPath);

        // 预定义响应体
        CloseableHttpResponse httpResponse = null;

        try {
            // 由客户端执行(发送)Get请求
            httpResponse = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity httpEntity = httpResponse.getEntity();
            System.out.println("接口响应状态为：" + httpResponse.getStatusLine());

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("响应体内容的长度为：" + httpEntity.getContentLength());
                System.out.println("响应内容为：" + EntityUtils.toString(httpEntity, "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void doGetWithParam(){
        // 创建httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 定义请求的参数
        // 使用setParameter或者addParameter增加参数，区别：setParameter会覆盖同名参数，addParameter可能会存在多个同名的参数
        URI uri = null;
        try {
            uri = new URIBuilder("http://www.baidu.com").setParameter("wd", "刘德华").build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("带有参数的url: " + uri.toString());

        HttpGet httpGet = new HttpGet(uri);

        // httpGet.setHeader(); // 可以通过setHeader()设置头

        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);

            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                //输出内容
                FileUtils.writeStringToFile(new File("./baidu.html"), content,"UTF-8");
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
