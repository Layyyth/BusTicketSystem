package model;

import java.time.LocalDate;

public class Trip {
    private int id;
    private String origin;
    private String destination;
    private LocalDate departureTime;
    private String travelType;

    public Trip(int id, String origin, String destination, LocalDate departureTime, String travelType) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.travelType = travelType;
    }

    public int getId() { return id; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public LocalDate getDepartureTime() { return departureTime; }
    public String getTravelType() { return travelType; }
}
