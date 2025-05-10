package model.strategy;

import config.FareConfig;

public class StudentFareStrategy implements FareStrategy {
    @Override
    public double calculateFare(String ticketType, String travelType) {
        double baseFare = FareConfig.getInstance().getBaseFare();
        double discount = FareConfig.getInstance().getStudentDiscount();
        return baseFare * (1 - discount);
    }
}
