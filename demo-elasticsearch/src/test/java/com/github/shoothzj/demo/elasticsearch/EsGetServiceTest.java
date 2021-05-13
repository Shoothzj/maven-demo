package com.github.shoothzj.demo.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

/**
 * @author zhangjian
 */
@Slf4j
public class EsGetServiceTest {

    @Test
    public void queryAll() throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        GetRequest getRequest = new GetRequest("posts", "1");
        final GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        log.info("response is {}", getResponse);
    }

}