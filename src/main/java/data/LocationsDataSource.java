package data;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import exception.DistanceBetweenLocationsException;
import location.LocationEntity;
import org.bson.Document;
import tracker.TrackerEntity;
import tracker.TrackerService;
import tracker.TrackerServiceImpl;
import util.Constants;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class LocationsDataSource {

    private MongoCollection<Document> locationsCollection;
    private TrackerService trackerService;

    public LocationsDataSource(){
        locationsCollection = new Database().getLocationCollection();
        trackerService = new TrackerServiceImpl(new TrackerDataSource());
    }

    public LocationEntity create(LocationEntity locationToSave) throws DistanceBetweenLocationsException {
        Document document = new Document();
        LocationEntity lastLocation = getLastLocation();
        if(distanceBetweenLocations(
                locationToSave.getLatitude(),
                locationToSave.getLongitude(),
                lastLocation.getLatitude(),
                lastLocation.getLongitude()) > Constants.MIN_DISTANCE_BETWEEN_LOCATIONS) {
            int currentId = lastLocation.getId() + 1;
            locationToSave.setId(currentId);
            document.append(Constants.FIELD_ID, locationToSave.getId());
            document.append(Constants.FIELD_DATE_TIME, locationToSave.getDateTime());
            document.append(Constants.FIELD_TRACKER_ID, locationToSave.getTrackerId());
            document.append(Constants.FIELD_LATITUDE, locationToSave.getLatitude());
            document.append(Constants.FIELD_LONGITUDE, locationToSave.getLongitude());
            locationsCollection.insertOne(document);
            TrackerEntity tracker = new TrackerEntity(locationToSave.getTrackerId(), locationToSave.getLatitude(), locationToSave.getLongitude());
            trackerService.updateTracker(tracker);
        }else{
            throw new DistanceBetweenLocationsException("Distance between the last location and the location you want to create is smaller than 10 meters.");
        }
        return locationToSave;
    }

    public ArrayList<LocationEntity> getAll(){
        ArrayList<LocationEntity> locationEntities = new ArrayList<>();
        try {
            FindIterable<Document> documents = locationsCollection.find(new Document()); //.sort(new BasicDBObject(Constants.FIELD_ID, -1));
            documents.forEach((Block<? super Document>) item -> {
                LocationEntity locationEntity = new Gson().fromJson(item.toJson(), LocationEntity.class);
                locationEntities.add(locationEntity);
            });
        }catch (Exception e){
            System.out.println(e.getCause());
        }
        return locationEntities;
    }

    public LocationEntity findById(int id){
        LocationEntity locationEntity = null;
        try {
            Document document = locationsCollection.find(new Document(Constants.FIELD_ID, id)).first();
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
            Document found = locationsCollection.find(new Document(Constants.FIELD_ID, id)).first();
            if(found != null){
                locationEntity = buildUpdatedLocationEntity(bodyHash);
                locationEntity.setId(id);

                String fixedDateTime = fixDateTimeFormat(bodyHash.get(Constants.FIELD_DATE_TIME));
                Document dateTime = new Document(Constants.FIELD_DATE_TIME, fixedDateTime);
                Document trackerId = new Document(Constants.FIELD_TRACKER_ID, Integer.parseInt(bodyHash.get(Constants.FIELD_TRACKER_ID)));
                Document latitude = new Document(Constants.FIELD_LATITUDE, Double.parseDouble(bodyHash.get(Constants.FIELD_LATITUDE)));
                Document longitude = new Document(Constants.FIELD_LONGITUDE, Double.parseDouble(bodyHash.get(Constants.FIELD_LONGITUDE)));

                locationsCollection.updateOne(eq(Constants.FIELD_ID, id), new Document("$set", dateTime));
                locationsCollection.updateOne(eq(Constants.FIELD_ID, id), new Document("$set", trackerId));
                locationsCollection.updateOne(eq(Constants.FIELD_ID, id), new Document("$set", latitude));
                locationsCollection.updateOne(eq(Constants.FIELD_ID, id), new Document("$set", longitude));
            }
        }catch (NullPointerException e){ }
        catch (Exception e){
            e.printStackTrace();
        }
        return locationEntity;
    }

    public LocationEntity delete(int id){
        Document deletedLocation = locationsCollection.findOneAndDelete(new Document(Constants.FIELD_ID, id));
        return new Gson().fromJson(deletedLocation.toJson(), LocationEntity.class);
    }

    private LocationEntity getLastLocation() {
        Document search = locationsCollection.find(new Document()).sort(new BasicDBObject(Constants.FIELD_ID, -1)).first();
        return new Gson().fromJson(search.toJson(), LocationEntity.class);
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

    private double distanceBetweenLocations(double latitude1, double longitude1, double latitude2, double longitude2){
        int radiusOfEarth = 6371;
        int meters = 1000;
        double distanceLatitude = Math.toRadians(latitude2 - latitude1);
        double distanceLongitude = Math.toRadians(longitude1 - longitude2);
        double a = Math.sin(distanceLatitude / 2) * Math.sin(distanceLatitude / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(distanceLongitude / 2) * Math.sin(distanceLongitude / 2);
        double c = Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * 2;
        double distance = radiusOfEarth * c * meters;
        distance = Math.pow(distance, 2) + Math.pow(0, 2);
        return Math.sqrt(distance);
    }

}
