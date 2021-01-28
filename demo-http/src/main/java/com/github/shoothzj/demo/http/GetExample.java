package com.github.shoothzj.demo.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;

/**
 * @author hezhangjian
 */
@Slf4j
public class GetExample {

    public static void main(String[] args) throws Exception {
        final URI uri = new URIBuilder().setScheme("http").setHost("127.0.0.1").setPort(8080).setPath("/metrics").build();
        final HttpGet httpGet = new HttpGet(uri);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpGet);
        final int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        final String toString = EntityUtils.toString(closeableHttpResponse.getEntity());
        final String[] split = toString.split(System.lineSeparator());
        System.out.println(toString);
    }

}
