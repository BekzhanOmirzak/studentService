package com.studentservice.demo.configuration;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {


        @Bean
        public AmazonS3 provideAmazonS3(){
            AWSCredentials awsCredentials=new BasicAWSCredentials(
                    "AKIA57IEMGBJXKQCRZND",
                    "7nl5RyjKdmdyg5C5pmMG+rzQO6R4952L1FYs5HEV"
            );
            return AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion("us-east-2")
                    .build();
        }



}
