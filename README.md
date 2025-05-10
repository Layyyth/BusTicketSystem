# 🚌 Bus Ticket System (Java Servlet + JSP + PostgreSQL)

This project is a complete **Web-based Bus Ticketing System** developed using **Java Servlets, JSP, JDBC**, and **PostgreSQL**, designed for managing user bookings, trip administration, dynamic pricing, and ticket lifecycle.

---

## 🔧 Features

### 👤 Passenger:
- Register & login securely
- Search for trips (City or Inter-City)
- Book tickets dynamically
- Automatic fare calculation (with discounts)
- View & cancel tickets

### 👮 Admin:
- Add/manage trips
- View trip reports
- Configure fare rules via Singleton `FareConfig`
- Set discounts for:
  - Student
  - Senior
  - Evening trips (after 7 PM)
- Validation: Max 50% total discount
- Prevent overbooking (thread-safe seat handling)

---

## ⚙️ Technologies Used

- **Java Servlet + JSP**
- **JDBC with PostgreSQL**
- **HTML/CSS + JSP Forms**
- **Maven** (project build tool)
- **Tomcat 10.1** (server runtime)
- **JUnit 5** (unit testing)

---

## 🗃 Database Schema Overview

### `users`
| id | username | password | role |
|----|----------|----------|------|

### `trips`
| id | origin | destination | departure_date | travel_type | seats_available |

### `tickets`
| id | trip_id | passenger_username | ticket_type | purchase_date | fare |

---

## 🧠 Design Patterns Used

- **Factory Pattern** for generating various `Ticket` types
- **Strategy Pattern** for modular fare calculation
- **Singleton Pattern** for shared `FareConfig` settings

---

## 🛠 How to Run Locally

1. **Clone the repo**

```bash
git clone https://github.com/Layyyth/BusTicketSystem.git
cd BusTicketSystem

2. Configure PostgreSQL
Create a database:
CREATE DATABASE bus_ticketing;
Run the SQL script (provided in /sql/ or manually set up tables: users, trips, tickets).

3. Update DB Credentials
In Util/DBUtil.java, update:
private static final String URL = "jdbc:postgresql://localhost:5432/bus_ticketing";
private static final String USER = "your_postgres_username";
private static final String PASSWORD = "your_postgres_password";

4. Run the App
Open in IntelliJ IDEA
Run on Tomcat 10
Visit: http://localhost:8081/BusTicketSystem

------------------------------------------------------

📂 Project Structure
├── model/              # Core models (User, Trip, Ticket)
├── servlet/            # Login, Booking, Trip, Config handlers
├── config/             # FareConfig singleton
├── strategy/           # Fare strategies
├── factory/            # TicketFactory
├── repository/         # DB interaction
├── jsp/                # JSP views (login, admin panel, tickets)
├── Util/               # DB Utility
├── test/               # JUnit test classes
└── pom.xml             # Maven config

--------------------------------------------------------

🧪 Testing
Run JUnit tests from:
src/test/java/
Tests include:
    -Trip creation
    -Ticket booking
    -Strategy fare correctness
    -Singleton fare config updates
    -Booking edge cases (overbooking)


-------------------------------------------------------
 Authors
Developed by Laith Aj
