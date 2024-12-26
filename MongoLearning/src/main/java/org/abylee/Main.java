package org.abylee;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.IOException;

public class Main {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017/?minPoolSize=1&maxPoolSize=5";

    public static void main(String[] args) throws IOException {
        // MongoClient is a thread-safe pool of connection
        MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);

        // Get DB & Collection
        MongoDatabase peppolDB = mongoClient.getDatabase("xmltest");
        MongoCollection<Document> invoiceCollection = peppolDB.getCollection("xml");

        // insert xml file
//        byte[] xmlFileBytes = Files.readAllBytes(Paths.get("src/main/resources/log4j2.xml"));
//        String xml =  new String(xmlFileBytes);
//        Document standardBusinessDocument = new Document()
//                .append("name", "BD")
//                .append("value", 1)
//                .append("BD", xmlFileBytes);
//        invoiceCollection.insertOne(standardBusinessDocument);

        // read xml back again
        invoiceCollection.find(Filters.eq("value", 1)).forEach((document -> {
            Binary bd = (Binary) document.get("BD");
            System.out.println(new String(bd.getData()));
        }));

        // Close the pool
        mongoClient.close();
    }
}