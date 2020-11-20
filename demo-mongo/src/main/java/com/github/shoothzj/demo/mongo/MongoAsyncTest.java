package com.github.shoothzj.demo.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
public class MongoAsyncTest {

    public static void main(String[] args) {
        List<ServerAddress> servers = new ArrayList<>();
        servers.add(new ServerAddress("localhost", 27018));
        //配置构建器
        MongoClientSettings.Builder settingsBuilder = MongoClientSettings.builder();
        //传入服务器实例
        settingsBuilder.applyToClusterSettings(builder -> builder.hosts(servers));
        //构建 Client 实例
        MongoClient mongoClient = MongoClients.create(settingsBuilder.build());

        final MongoDatabase database = mongoClient.getDatabase("databaseName");
        final MongoCollection<Document> collection = database.getCollection("collectionName");
        final FindPublisher<Document> findPublisher = collection.find();
        findPublisher.subscribe(new Subscriber<Document>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                log.info("start ...");
                subscription.request(Integer.MAX_VALUE);
            }

            @Override
            public void onNext(Document document) {
                log.info("document is [{}]", document.toJson());
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("error occurs.");
            }

            @Override
            public void onComplete() {
                log.info("finished.");
            }
        });
    }

}
