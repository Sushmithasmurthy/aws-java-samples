package com.ss.utility;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;


public class ExportTableData {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportTableData.class);


    public static boolean export(AmazonDynamoDB dynamoDB, AmazonS3 s3Client, String bucketName, String targetS3FileName) {
        try {
            DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<Book> records = mapper.scan(Book.class, scanExpression);
            records
                    .forEach(x -> LOGGER.info(getObjectAsString(x)));
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytesToWrite = objectMapper.writeValueAsBytes(records);

            ObjectMetadata omd = new ObjectMetadata();
            omd.setContentLength(bytesToWrite.length);
            s3Client.putObject(bucketName, targetS3FileName,
                    new ByteArrayInputStream(bytesToWrite), omd);


        } catch (JsonProcessingException | AmazonServiceException e) {
            LOGGER.error("Failed to retrieve items.", e);
        }
        return true;
    }


    public static <T> String getObjectAsString(T data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(data);
            return json;
        } catch (JsonProcessingException ex) {
            return null;
        }
    }


}
/*
            //put data top file
            String filepath = FileSystems.getDefault().getPath(".").toString() + "/backupdata.json";

            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(
                            filepath))) {
                objectOutputStream.writeObject(records);
            } catch (IOException ex) {
                LOGGER.error("Something went wrong", ex);
            }*/