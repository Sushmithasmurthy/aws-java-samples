package com.ss.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ss.utility.ExportTableData;

public class ExportDynamoToS3 implements RequestHandler<String, Boolean> {

    @Override
    public Boolean handleRequest(String s, Context context) {
         AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
         AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
        return ExportTableData.export(client,s3Client,"sush-s3-test", "backupdata.json");

    }
}
