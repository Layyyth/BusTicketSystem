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
