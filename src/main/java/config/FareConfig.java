package config;

public class FareConfig {
    private static FareConfig instance;
    private double baseFare = 1.0;
    private double studentDiscount = 0.3;
    private double seniorDiscount = 0.2;
    private double eveningDiscount = 0.15;

    private FareConfig() {} // Private constructor

    public static FareConfig getInstance() {
        if (instance == null) {
            instance = new FareConfig();
        }
        return instance;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public double getStudentDiscount() {
        return studentDiscount;
    }

    public void setStudentDiscount(double studentDiscount) {
        this.studentDiscount = studentDiscount;
    }

    public double getSeniorDiscount() {
        return seniorDiscount;
    }

    public void setSeniorDiscount(double seniorDiscount) {
        this.seniorDiscount = seniorDiscount;
    }

    public double getEveningDiscount() {
        return eveningDiscount;
    }

    public void setEveningDiscount(double eveningDiscount) {
        this.eveningDiscount = eveningDiscount;
    }
}
