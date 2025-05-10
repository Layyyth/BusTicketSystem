package factory;

import model.strategy.FareStrategy;
import model.tickets.*;

import java.time.LocalDate;

public class TicketFactory {
    public static Ticket createTicket(
            String ticketType,
            int tripId,
            String passengerUsername,
            LocalDate purchaseDate,
            FareStrategy strategy,
            String travelType
    ) {
        return switch (ticketType.toUpperCase()) {
            case "ONE_TRIP" -> new OneTripTicket(tripId, passengerUsername, purchaseDate, strategy, travelType);
            case "DAILY_PASS" -> new DailyPassTicket(tripId, passengerUsername, purchaseDate, strategy, travelType);
            case "WEEKLY_PASS" -> new WeeklyPassTicket(tripId, passengerUsername, purchaseDate, strategy, travelType);
            case "MONTHLY_PASS" -> new MonthlyPassTicket(tripId, passengerUsername, purchaseDate, strategy, travelType);
            default -> throw new IllegalArgumentException("Invalid ticket type: " + ticketType);
        };
    }
}
