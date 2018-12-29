package data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import util.Constants;

class Database {

    final private MongoClientURI uri = new MongoClientURI(Constants.MONGO_CLIENT_URI);
    final private MongoClient mongoClient = new MongoClient(uri);
    final private MongoDatabase database = mongoClient.getDatabase(Constants.DATABASE_NAME);
    final private MongoCollection<Document> locationsCollection = database.getCollection(Constants.LOCATIONS_COLLECTION_NAME);
    final private MongoCollection<Document> trackerCollection = database.getCollection(Constants.TRACKER_COLLECTION_NAME);

    public MongoCollection<Document> getLocationCollection(){ return locationsCollection; }
    public MongoCollection<Document> getTrackerCollection(){ return trackerCollection; }

}
