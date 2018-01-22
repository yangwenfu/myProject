package com.xinyunlian.jinfu.configuration;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author Sephy
 * @since: 2017-06-28
 */
@Configuration
public class MongoAutoConfiguration {

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private int port;

    @Value("${mongo.database}")
    private String database;

    @Value("${mongo.username}")
    private String username;

    @Value("${mongo.password}")
    private String password;

    @Bean
    public MongoTemplate getMongoTemplate() throws UnknownHostException {
        MongoDbFactory mongoDbFactory = getMongoDbFactory();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, getMappingMongoConverter(mongoDbFactory));
        return mongoTemplate;
    }

    @Bean
    public MappingMongoConverter getMappingMongoConverter(MongoDbFactory mongoDbFactory) {
        return new MappingMongoConverter(
                new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
    }

    @Bean
    public MongoDbFactory getMongoDbFactory() throws UnknownHostException {
        MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();
        ServerAddress serverAddress = new ServerAddress(host, port);
        List<MongoCredential> credentialsList = Lists.newArrayList(MongoCredential.createScramSha1Credential(database, username, password.toCharArray()));
        MongoClient mongoClient = new MongoClient(serverAddress, credentialsList, optionsBuilder.build());
        SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, database);
        return mongoDbFactory;
    }

}
