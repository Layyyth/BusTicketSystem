package model.strategy;

import config.FareConfig;

public class EveningFareStrategy implements FareStrategy {
    private final FareStrategy wrapped;

    public EveningFareStrategy(FareStrategy wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double calculateFare(String ticketType, String travelType) {
        double fare = wrapped.calculateFare(ticketType, travelType);

        boolean isEligible = "ONE_TRIP".equalsIgnoreCase(ticketType) &&
                java.time.LocalTime.now().isAfter(java.time.LocalTime.of(19, 0));

        if (isEligible) {
            double eveningDiscount = FareConfig.getInstance().getEveningDiscount();
            double totalDiscount = 1 - (fare / FareConfig.getInstance().getBaseFare()) + eveningDiscount;

            // Cap total discounts to 50%
            if (totalDiscount > 0.5) {
                eveningDiscount = 0.5 - (1 - (fare / FareConfig.getInstance().getBaseFare()));
            }

            fare *= (1 - eveningDiscount);
        }

        return fare;
    }
}
