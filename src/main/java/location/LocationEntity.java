package location;


public class LocationEntity {
    private int id;
    private String dateTime;
    private Integer trackerId;
    private Double latitude;
    private Double longitude;

    public LocationEntity(){ }

    public LocationEntity(String dateTime, Integer trackerId, Double latitude, Double longitude){
        this.dateTime = dateTime;
        this.trackerId = trackerId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id; }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(Integer trackerId) {
        this.trackerId = trackerId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "\"id\": "+getId()+",\n" +
                "\"dateTime\": \""+getDateTime()+"\",\n" +
                "\"trackerId\": "+getTrackerId()+",\n" +
                "\"latitude\": "+getLatitude()+",\n" +
                "\"longitude\": "+getLongitude()+"\n";
    }
}
