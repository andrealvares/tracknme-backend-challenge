package tracker;

public class TrackerEntity {
    private int id;
    private String name;
    private double latitude;
    private double longitude;

    public TrackerEntity(){}
    public TrackerEntity(String name, double latitude, double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public TrackerEntity(int id, double latitude, double longitude){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\": "+getId()+",\n" +
                "\"name\": \""+getName()+"\",\n" +
                "\"latitude\": "+getLatitude()+",\n" +
                "\"longitude\": "+getLongitude()+"\n" +
                "}";
    }
}
