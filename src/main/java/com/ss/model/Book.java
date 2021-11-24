package com.ss.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@DynamoDBTable(tableName = "Books")
public class Book {
    private String title;
    private String author;
    private String country;
    private String imageLink;
    private String language;
    private String link;
    private Integer pages;
    private Integer year;

    @DynamoDBHashKey(attributeName = "title")
    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    @DynamoDBAttribute(attributeName = "author")
    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    @DynamoDBAttribute(attributeName = "country")
    public String getCountry() {
        return country;
    }

    public Book setCountry(String country) {
        this.country = country;
        return this;
    }

    @DynamoDBAttribute(attributeName = "imageLink")
    public String getImageLink() {
        return imageLink;
    }

    public Book setImageLink(String imageLink) {
        this.imageLink = imageLink;
        return this;
    }

    @DynamoDBAttribute(attributeName = "language")
    public String getLanguage() {
        return language;
    }

    public Book setLanguage(String language) {
        this.language = language;
        return this;
    }

    @DynamoDBAttribute(attributeName = "link")
    public String getLink() {
        return link;
    }

    public Book setLink(String link) {
        this.link = link;
        return this;
    }

    @DynamoDBAttribute(attributeName = "pages")
    public Integer getPages() {
        return pages;
    }

    public Book setPages(Integer pages) {
        this.pages = pages;
        return this;
    }

    @DynamoDBAttribute(attributeName = "year")
    public Integer getYear() {
        return year;
    }

    public Book setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Map<String, String> toMap() {
        return new ObjectMapper().convertValue(this, new TypeReference<Map<String, String>>() {
        });
    }
}
