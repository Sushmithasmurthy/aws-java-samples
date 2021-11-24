package com.ss.lambda;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


public class ImportS3ToDynamo implements RequestHandler<S3EventNotification, Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportS3ToDynamo.class);

    @Override
    public Boolean handleRequest(S3EventNotification s3Event, Context context) {

        S3EventNotification.S3EventNotificationRecord record = s3Event.getRecords().get(0);
        String srcKey = record.getS3().getObject().getUrlDecodedKey();

        String bucket_name = "sush-s3-test";
        String key_name = "backupdata.json";
        LOGGER.info("Print contyents of scheduled event" + srcKey);

        LOGGER.info("If you need more data" + record.getS3().getBucket().getName());
        LOGGER.info("Downloading " + key_name + " from S3 bucket " + bucket_name);

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        try {
            S3Object o = s3.getObject(bucket_name, key_name);
            S3ObjectInputStream s3is = o.getObjectContent();
            String str = getAsString(s3is);
            LOGGER.info("Response String: " + str);

            ObjectMapper mapper = new ObjectMapper();
            List<Book> bookList = Arrays.asList(mapper.readValue(str, Book[].class));

            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
            DynamoDBMapper dbMapper = new DynamoDBMapper(client);
            for (Book bk: bookList) {
                LOGGER.info(bk.toString());
                dbMapper.save(bk);
            }
        } catch (AmazonServiceException e) {
            LOGGER.error(e.getErrorMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Done!");
        return null;
    }

    private static String getAsString(InputStream is) throws IOException {
        if (is == null)
            return "";
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, StringUtils.UTF8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        return sb.toString();
    }
}


