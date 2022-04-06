package com.starry.starrycore.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ESHighLevelFactory {

    public static RestHighLevelClient getRestHighLevelClient(String hostName, int port) {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(hostName, port, "http"))
        );
        return client;
    }
}
