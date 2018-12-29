package data;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import tracker.TrackerEntity;
import util.Constants;
import static com.mongodb.client.model.Filters.eq;

public class TrackerDataSource {

    private MongoCollection<Document> trackerCollection;

    public TrackerDataSource(){
        trackerCollection = new Database().getTrackerCollection();
    }

    public void updateTrackerCoordinates(TrackerEntity trackerEntity){
        Document found = trackerCollection.find(new Document(Constants.FIELD_ID, trackerEntity.getId())).first();
        if(found != null) {
            Document latitude = new Document(Constants.FIELD_LATITUDE, trackerEntity.getLatitude());
            Document longitude = new Document(Constants.FIELD_LONGITUDE, trackerEntity.getLongitude());
            trackerCollection.updateOne(eq(Constants.FIELD_ID, trackerEntity.getId()), new Document("$set", latitude));
            trackerCollection.updateOne(eq(Constants.FIELD_ID, trackerEntity.getId()), new Document("$set", longitude));
        }
    }
}