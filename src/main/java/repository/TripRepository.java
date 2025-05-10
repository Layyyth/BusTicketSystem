package repository;

import model.Trip;
import Util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TripRepository {

    public static void addTrip(String origin, String destination, LocalDate departureDate, String travelType) {
        String sql = "INSERT INTO trips (origin, destination, departure_date, travel_type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, origin);
            stmt.setString(2, destination);
            stmt.setObject(3, departureDate);
            stmt.setString(4, travelType);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT * FROM trips ORDER BY id ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Trip trip = new Trip(
                        rs.getInt("id"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getObject("departure_date", LocalDate.class),
                        rs.getString("travel_type")
                );
                trips.add(trip);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trips;
    }

    public static String getTravelTypeByTripId(int tripId) {
        String travelType = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT travel_type FROM trips WHERE id = ?")
        ) {
            stmt.setInt(1, tripId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                travelType = rs.getString("travel_type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return travelType;
    }
}
