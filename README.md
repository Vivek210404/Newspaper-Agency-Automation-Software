# Newspaper Agency Automation

Java Swing desktop application for managing a newspaper/magazine agency
workflow, including customer management, subscriptions, billing,
payments, due tracking, and delivery commission simulation.

## Features

-   Customer management (add, update, delete)
-   Publication and subscription management (newspapers + magazines)
-   Monthly bill generation based on active subscriptions
-   Payment recording (cash/cheque) with receipt generation
-   Due tracking rules:
    -   1+ month overdue -\> reminder sent
    -   2+ months overdue -\> customer deactivated and subscriptions
        disabled
-   Delivery cycle simulation
-   Delivery person commission calculation (2.5%)

## Tech Stack

-   Java 17
-   Java Swing (desktop UI)
-   Maven

## Project Structure

    src/
      agency/
        Main.java
        model/
        service/
        store/
        ui/
        util/
    pom.xml

## Prerequisites

-   JDK 17+
-   Maven 3.8+

## Getting Started

### 1) Clone and open the project
```bash
git clone https://github.com/Vivek210404/Newspaper-Agency-Automation-Software
cd Newspaper-Agency-Automation-Software
```

### 2) Run the app (development)
```bash
mvn clean compile exec:java
```

### 3) Build runnable JAR
```bash
mvn clean package
```

Then run:
```bash
java -jar target/newspaper-agency-automation-1.0.0.jar
```

## How the App Works

When the app starts: - In-memory datastore is initialized - Sample data
is loaded (customers, publications, subscriptions, delivery staff) -
Services are wired and the main dashboard opens

Main dashboard modules: - Manage Customers - Manage Subscriptions -
Generate Bills - Record Payments - View Reports

## Notes

-   Data is stored in memory only (DataStore) and is not persisted to a
    database/file.
-   Restarting the application resets data to fresh sample data.

## Sample Usage Flow

-   Add or update customers
-   Create publications/subscriptions
-   Generate bills for a month
-   Record customer payments
-   Apply due tracking for a month (YYYY-MM)
-   Simulate delivery cycle and view commission updates