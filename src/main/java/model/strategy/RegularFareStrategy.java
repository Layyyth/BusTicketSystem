package model.strategy;

import config.FareConfig;

public class RegularFareStrategy implements FareStrategy {
    @Override
    public double calculateFare(String ticketType, String travelType) {
        return FareConfig.getInstance().getBaseFare();
    }
}
