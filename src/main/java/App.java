import com.google.gson.Gson;
import data.LocationsDataSource;
import data.TrackerDataSource;
import exception.DistanceBetweenLocationsException;
import location.LocationEntity;
import location.LocationService;
import location.LocationServiceImpl;
import tracker.TrackerEntity;
import tracker.TrackerService;
import tracker.TrackerServiceImpl;
import util.Path;
import java.util.List;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.port;
import static spark.Spark.path;
import static spark.Spark.patch;

public class App {
    public static void main(String[] args) {
        /* INITIAL SETUP */
        port(8080);
        LocationsDataSource locationsDataSource = new LocationsDataSource();
        LocationService locationService = new LocationServiceImpl(locationsDataSource);
        Gson gson = new Gson();

        get("/" , "application/json", (request, response) -> {
           return "<a href='/locations'>Locations ~></a>";
        });

        path(Path.LOCATIONS, () -> {
            post(Path.ADD_LOCATION, "application/json", (request, response) -> {
                if(request.queryParams("dateTime") != null && request.queryParams("trackerId") != null &&
                        request.queryParams("latitude") != null && request.queryParams("longitude") != null){
                    LocationEntity locationToSave = new LocationEntity(
                            request.queryParams("dateTime"),
                            Integer.parseInt(request.queryParams("trackerId")),
                            Double.parseDouble(request.queryParams("latitude")),
                            Double.parseDouble(request.queryParams("longitude")));
                    try{
                        return locationService.create(locationToSave);
                    }catch (DistanceBetweenLocationsException e){
                        return e.getMessage();
                    }

                }
                return "All parameters are required! Check the documentation for understand the parameters.";
            }, gson::toJson);

            get(Path.ROOT,"application/json", (request, response)-> {
                List<LocationEntity> locations = locationService.listAll();
                if(locations == null){
                    return "{ error: No results! }";
                }
                return locations;
            }, gson::toJson);

            get(Path.LOCATION_BY_ID, "application/json", (request, response) -> {
                int id = Integer.parseInt(request.params("id"));
                LocationEntity locationEntity = locationService.findById(id);
                if(locationEntity == null){
                    return "{ error: No location found! }";
                }
                return locationEntity;
            }, gson::toJson);

            patch(Path.LOCATION_BY_ID, "application/json",(request, response) -> {
                int id = Integer.parseInt(request.params("id"));
                String bodyRequest = request.body();
                if(bodyRequest.trim().equals("")){
                    return "{ error: At least one field should be passed! }";
                }
                return locationService.update(id, bodyRequest);
            });

            delete(Path.LOCATION_BY_ID, "application/json",(request, response) -> {
                int id = Integer.parseInt(request.params("id"));
                return locationService.delete(id);
            }, gson::toJson);
        });
    }
}
