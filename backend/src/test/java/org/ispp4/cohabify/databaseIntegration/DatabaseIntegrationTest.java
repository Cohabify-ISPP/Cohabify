package org.ispp4.cohabify.databaseIntegration;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseIntegrationTest {
    private MongoClient mongoClient;
    private MongoDatabase database;

    @BeforeAll
    public void initializeDatabase() {
        // Inicie una base de datos
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("mydatabase");
    }

    @Test
    public void testDatabaseWrite() {
        // Active una función dentro de su código que escriba datos en la base de datos
        MongoCollection<Document> collection = database.getCollection("mycollection");
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));
        collection.insertOne(doc);

        // Verifique que los datos esperados se hayan escrito en la base de datos leyendo los datos de la misma
        Document myDoc = collection.find(doc).first();
        Assertions.assertNotNull(myDoc, "Expected data not found in the database");
    }

    @AfterAll
    public void closeDatabase() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
