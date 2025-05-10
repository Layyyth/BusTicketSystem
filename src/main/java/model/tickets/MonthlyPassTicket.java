package model.tickets;

import model.strategy.FareStrategy;
import repository.TripRepository;

import java.time.LocalDate;

public class MonthlyPassTicket extends Ticket {
    private FareStrategy fareStrategy;

    public MonthlyPassTicket(int tripId, String passengerUsername, LocalDate purchaseDate, FareStrategy fareStrategy, String travelType) {
        super(tripId, passengerUsername, purchaseDate);
        this.fareStrategy = fareStrategy;
    }

    @Override
    public void calculateFare() {
        String travelType = TripRepository.getTravelTypeByTripId(tripId);
        this.fare = fareStrategy.calculateFare("MONTHLY_PASS", travelType);
    }
}
