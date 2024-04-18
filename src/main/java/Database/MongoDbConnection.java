package Database;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClients;

public class MongoDbConnection {

    private MongoClient mongoClient;
    private String uri = "mongodb://localhost:27017";
    private String database = "database";

    public void init() {
        mongoClient = MongoClients.create(uri);
    }
    public MongoDatabase getDatabase() {
        MongoDatabase db = mongoClient.getDatabase(database);
        System.out.println("Connected to database");
        return db;
    }
}
