package com.EstimatedDeliveryDate.Config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/*Configuring with credentials to access the DynamoDB*/

@Configuration
public class EstimatedDeliveryDateConfig {
	// providing credentials and region to access the DynamoDB
	public AmazonDynamoDB amazonDynamoDBConfig() {
		ProfileCredentialsProvider credentialProvider = new ProfileCredentialsProvider();
		credentialProvider.getCredentials();
		return AmazonDynamoDBClientBuilder.standard().withCredentials(credentialProvider).withRegion("us-east-1")
				.build();
	}

	// Created bean for dependency injection
	@Bean
	public DynamoDBMapper mapper() {
		return new DynamoDBMapper(amazonDynamoDBConfig());
	}

}
