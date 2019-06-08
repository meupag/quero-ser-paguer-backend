package com.pag.bd;


import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.pag.db.repository")
public class DynamoDBConfig {
 
    @Value("${amazon.end-point.url}")
    private String amazonDynamoDBEndpoint;
 
    @Value("${amazon.access.key}")
    private String amazonAWSAccessKey;
 
    @Value("${amazon.access.secret-key}")
    private String amazonAWSSecretKey;

    @Value("${amazon.access.region}")
    private String regiao;

	@Bean
	public DynamoDBMapper mapper() {
		return new DynamoDBMapper(amazonDynamoDBConfig());
	}

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		return amazonDynamoDBConfig();
	}
	
	public AmazonDynamoDB amazonDynamoDBPadrao() {
		return AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, regiao))
				.withCredentials(new DefaultAWSCredentialsProviderChain()).build();
	}
	
	public AmazonDynamoDB amazonDynamoDBConfig() {
		return AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, regiao))
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey))).build();
	}
}