package data;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import location.LocationEntity;
import org.bson.Document;
import org.bson.conversions.Bson;
import util.Constants;

import java.awt.*;
import java.util.*;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class LocationsDataSource {

    final private MongoClientURI uri = new MongoClientURI(Constants.MONGO_CLIENT_URI);
    final private MongoClient mongoClient = new MongoClient(uri);
    final private  MongoDatabase database = mongoClient.getDatabase(Constants.DATABASE_NAME);
    final private MongoCollection<Document> collection = database.getCollection(Constants.COLLECTION_NAME);

    public LocationsDataSource(){ }

    public LocationEntity create(LocationEntity locationToSave){
        Document document = new Document();
        int currentId = getLastId()+1;
        locationToSave.setId(currentId);
        document.append(Constants.FIELD_ID, locationToSave.getId());
        document.append(Constants.FIELD_DATE_TIME, locationToSave.getDateTime());
        document.append(Constants.FIELD_TRACKER_ID, locationToSave.getTrackerId());
        document.append(Constants.FIELD_LATITUDE, locationToSave.getLatitude());
        document.append(Constants.FIELD_LONGITUDE, locationToSave.getLongitude());
        collection.insertOne(document);
        return locationToSave;
    }

    public ArrayList<LocationEntity> getAll(){
        FindIterable<Document> documents = collection.find(new Document()).sort(new BasicDBObject(Constants.FIELD_ID, -1));
        ArrayList<LocationEntity> locationEntities = new ArrayList<>();
        documents.forEach((Block<? super Document>) item -> {
            LocationEntity locationEntity = new Gson().fromJson(item.toJson(), LocationEntity.class);
            locationEntities.add(locationEntity);
        });
        return locationEntities;
    }

    public LocationEntity findById(int id){
        LocationEntity locationEntity = null;
        try {
            Document document = collection.find(new Document(Constants.FIELD_ID, id)).first();
            locationEntity = new Gson().fromJson(document.toJson(), LocationEntity.class);
        }catch (Exception e){}

        return locationEntity;
    }

    public LocationEntity update(int id, String fieldsToBeUpdated){
        LocationEntity locationEntity = new LocationEntity();
        try {
            String[] body = fieldsToBeUpdated.split("&");
            HashMap<String, String> bodyHash = new HashMap<>();
            for (String param : body) {
                String[] params = param.split("=");
                bodyHash.put(params[0], params[1]);
            }
            Document found = collection.find(new Document(Constants.FIELD_ID, id)).first();
            if(found != null){
                locationEntity = buildUpdatedLocationEntity(bodyHash);
                locationEntity.setId(id);

                String fixedDateTime = fixDateTimeFormat(bodyHash.get(Constants.FIELD_DATE_TIME));
                Document dateTime = new Document(Constants.FIELD_DATE_TIME, fixedDateTime);
                Document trackerId = new Document(Constants.FIELD_TRACKER_ID, Integer.parseInt(bodyHash.get(Constants.FIELD_TRACKER_ID)));
                Document latitude = new Document(Constants.FIELD_LATITUDE, Double.parseDouble(bodyHash.get(Constants.FIELD_LATITUDE)));
                Document longitude = new Document(Constants.FIELD_LONGITUDE, Double.parseDouble(bodyHash.get(Constants.FIELD_LONGITUDE)));

                collection.updateOne(eq(Constants.FIELD_ID, id), new Document("$set", dateTime));
                collection.updateOne(eq(Constants.FIELD_ID, id), new Document("$set", trackerId));
                collection.updateOne(eq(Constants.FIELD_ID, id), new Document("$set", latitude));
                collection.updateOne(eq(Constants.FIELD_ID, id), new Document("$set", longitude));
            }
        }catch (NullPointerException e){ }
        catch (Exception e){
            e.printStackTrace();
        }
        return locationEntity;
    }

    public LocationEntity delete(int id){
        Document deletedLocation = collection.findOneAndDelete(new Document(Constants.FIELD_ID, id));
        return new Gson().fromJson(deletedLocation.toJson(), LocationEntity.class);
    }

    private int getLastId() {
        Document search = collection.find(new Document()).sort(new BasicDBObject(Constants.FIELD_ID, -1)).first();
        LocationEntity location = new Gson().fromJson(search.toJson(), LocationEntity.class);
        return location.getId();
    }

    private LocationEntity buildUpdatedLocationEntity(HashMap<String, String> bodyHash){
        LocationEntity updatedLocation = new LocationEntity();
        if(bodyHash.containsKey(Constants.FIELD_DATE_TIME)){
            String updatedDateTime = fixDateTimeFormat(bodyHash.get(Constants.FIELD_DATE_TIME));
            updatedLocation.setDateTime(updatedDateTime);
        }
        if(bodyHash.containsKey(Constants.FIELD_TRACKER_ID)){
            updatedLocation.setTrackerId(Integer.parseInt(bodyHash.get(Constants.FIELD_TRACKER_ID)));
        }
        if(bodyHash.containsKey(Constants.FIELD_LATITUDE)){
            updatedLocation.setLatitude(Double.parseDouble(bodyHash.get(Constants.FIELD_LATITUDE)));
        }
        if(bodyHash.containsKey(Constants.FIELD_LONGITUDE)){
            updatedLocation.setLongitude(Double.parseDouble(bodyHash.get(Constants.FIELD_LONGITUDE)));
        }
        return updatedLocation;
    }

    private String fixDateTimeFormat(String wrongDateTime){
        return wrongDateTime.replace("%3A", ":");
    }

}
