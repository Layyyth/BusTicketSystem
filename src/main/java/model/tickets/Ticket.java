package model.tickets;

import java.time.LocalDate;

public abstract class Ticket {
    protected int tripId;
    protected String passengerUsername;
    protected LocalDate purchaseDate;
    protected double fare;

    public Ticket(int tripId, String passengerUsername, LocalDate purchaseDate) {
        this.tripId = tripId;
        this.passengerUsername = passengerUsername;
        this.purchaseDate = purchaseDate;
    }

    public abstract void calculateFare();

    public double getFare() {
        return fare;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }

    public int getTripId() {
        return tripId;
    }

    public String getPassengerUsername() {
        return passengerUsername;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
}
