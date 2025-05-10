import Util.DBUtil;
import model.User;
import model.strategy.*;
import model.tickets.Ticket;
import factory.TicketFactory;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BusTicketSystemTest {

    private static Connection conn;
    private static final String testUsername = "test_user_" + UUID.randomUUID();
    private static int testTripId;
    private static int testTicketId;

    @BeforeAll
    public static void setup() throws SQLException {
        conn = DBUtil.getConnection();
        assertNotNull(conn);
    }

    @Test
    @Order(1)
    public void testInsertUser() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
        stmt.setString(1, testUsername);
        stmt.setString(2, "testpass");
        stmt.setString(3, "PASSENGER");
        int affected = stmt.executeUpdate();
        assertEquals(1, affected);
    }

    @Test
    @Order(2)
    public void testCreateTrip() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO trips (origin, destination, departure_date, travel_type) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        stmt.setString(1, "Amman");
        stmt.setString(2, "Irbid");
        stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now().plusDays(1)));
        stmt.setString(4, "CITY");

        int affected = stmt.executeUpdate();
        assertEquals(1, affected);

        ResultSet rs = stmt.getGeneratedKeys();
        assertTrue(rs.next());
        testTripId = rs.getInt(1);
    }

    @Test
    @Order(3)
    public void testBookTicketWithStrategy() throws SQLException {
        FareStrategy baseStrategy = new StudentFareStrategy();
        FareStrategy fullStrategy = new EveningFareStrategy(baseStrategy);

        Ticket ticket = TicketFactory.createTicket(
                "ONE_TRIP",
                testTripId,
                testUsername,
                LocalDate.now(),
                fullStrategy,
                "CITY"
        );

        ticket.calculateFare();
        assertTrue(ticket.getFare() > 0);

        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO tickets (trip_id, passenger_username, ticket_type, purchase_date, fare) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        stmt.setInt(1, ticket.getTripId());
        stmt.setString(2, ticket.getPassengerUsername());
        stmt.setString(3, ticket.getType());
        stmt.setDate(4, java.sql.Date.valueOf(ticket.getPurchaseDate()));
        stmt.setDouble(5, ticket.getFare());

        int affected = stmt.executeUpdate();
        assertEquals(1, affected);

        ResultSet rs = stmt.getGeneratedKeys();
        assertTrue(rs.next());
        testTicketId = rs.getInt(1);
    }

    @Test
    @Order(4)
    public void testCancelTicket() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM tickets WHERE id = ?");
        stmt.setInt(1, testTicketId);
        int affected = stmt.executeUpdate();
        assertEquals(1, affected);
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        PreparedStatement delUser = conn.prepareStatement("DELETE FROM users WHERE username = ?");
        delUser.setString(1, testUsername);
        delUser.executeUpdate();

        PreparedStatement delTrip = conn.prepareStatement("DELETE FROM trips WHERE id = ?");
        delTrip.setInt(1, testTripId);
        delTrip.executeUpdate();

        if (conn != null) conn.close();
    }
}
