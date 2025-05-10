package model.tickets;

import model.strategy.FareStrategy;
import repository.TripRepository;

import java.time.LocalDate;

public class DailyPassTicket extends Ticket {
    private FareStrategy fareStrategy;

    public DailyPassTicket(int tripId, String passengerUsername, LocalDate purchaseDate, FareStrategy fareStrategy, String travelType) {
        super(tripId, passengerUsername, purchaseDate);
        this.fareStrategy = fareStrategy;
    }

    @Override
    public void calculateFare() {
        String travelType = TripRepository.getTravelTypeByTripId(tripId);
        this.fare = fareStrategy.calculateFare("DAILY_PASS", travelType);
    }
}
