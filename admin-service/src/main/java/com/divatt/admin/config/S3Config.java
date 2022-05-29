package com.divatt.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
public class S3Config {
		@Value("${cloud.aws.credentials.accessKey}")
	    String accessKey ;
//	    = "AKIAXMNFATE76NGUIJPB";
		
		@Value("${cloud.aws.credentials.secretKey}")
	    String accessSecret ;
//	    = "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";

	    @Bean
	    public AmazonS3Client generateS3Client() {
	        AWSCredentials credentials = new BasicAWSCredentials(accessKey,accessSecret);
	        AmazonS3Client client = new AmazonS3Client(credentials);
	        return client;
	    }


}
