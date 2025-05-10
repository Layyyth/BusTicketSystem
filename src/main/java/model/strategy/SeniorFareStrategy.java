package model.strategy;

import config.FareConfig;

public class SeniorFareStrategy implements FareStrategy {
    @Override
    public double calculateFare(String ticketType, String travelType) {
        double baseFare = FareConfig.getInstance().getBaseFare();
        double discount = FareConfig.getInstance().getSeniorDiscount();
        return baseFare * (1 - discount);
    }
}
