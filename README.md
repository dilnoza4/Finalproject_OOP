# UCA Counseling Appointment System - Backend

## Project Overview

The UCA Counseling Appointment System Backend is a RESTful API designed to support the frontend of the UCA Counseling Appointment System. This backend enables the management of counseling services, appointment scheduling, and user authentication (although the authentication feature was removed in the frontend version). It is built using **Java Spring Boot** and **Gradle** as the build tool. MySQL is used as the database for storing appointment data and user information.

This project is a joint effort between the frontend and backend teams, ensuring seamless interaction between the client-side interface and the server-side functionality.

## Technologies Used

- **Java Spring Boot**: A framework for building stand-alone, production-grade Spring-based applications.
- **Gradle**: The build tool used for managing dependencies and building the application.
- **MySQL**: A relational database management system for storing appointment data and user information.
- **Spring Data JPA**: For data persistence, enabling interaction between Java objects and the database.
- **Spring Security**: (Initially planned for user authentication, but was removed in the frontend).
- **JWT**: For secure token-based authentication (not implemented in the current version).
- **Postman**: For testing the backend API endpoints.

## Features Implemented

- **Service Management**: API for creating, updating, and retrieving counseling services.
- **Appointment Scheduling**: API for booking, updating, and retrieving appointments.
- **Database Integration**: MySQL database used for storing counseling services and appointments.
- **User Authentication**: Initially planned feature for user registration and login (removed in the frontend due to integration issues).

## Joint Project

This project was developed as a joint effort between the backend and frontend teams. The backend provides the essential API for handling appointments and services, while the frontend creates the user interface for students to interact with the system.

## Setup and Installation

### Prerequisites

Before setting up the backend, ensure that you have the following installed:

- **Java**: Ensure you have Java installed on your machine.
- **MySQL**: Install MySQL and set up a database for the project.
- **Gradle**: The build tool used to manage the project dependencies.

### Installation Steps

1. **Clone the Repository**:
    ```bash
    git clone <repository-url>
    cd <repository-folder>
    ```

2. **Set up the Database**:
   - Create a new database in MySQL (e.g., `uca_counseling`).
   - Update the `application.properties` (or `application.yml`) file with your database connection details:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/uca_counseling
     spring.datasource.username=<your-username>
     spring.datasource.password=<your-password>
     ```

3. **Install Dependencies**:
    - Run the following Gradle command to install dependencies:
      ```bash
      gradle build
      ```

4. **Run the Application**:
    - To run the Spring Boot application, use the following command:
      ```bash
      gradle bootRun
      ```

5. **Test the API**:
   - Use Postman or any API testing tool to test the endpoints:
     - `GET /api/services`: Retrieves a list of counseling services.
     - `POST /api/appointments`: Books a new appointment.


## Future Implementations

- **User Authentication**: Implement secure user authentication using JWT tokens or other modern authentication methods to allow users to register and log in.
- **Appointment Confirmation**: Send confirmation emails or notifications upon successful appointment booking.
- **Admin Panel**: Develop an admin panel to allow counselors to view and manage appointments.
- **Appointment Reminders**: Send automated reminders for upcoming appointments.

## Contributing

If you would like to contribute to the project, feel free to fork the repository, make improvements, and create a pull request. Please ensure that you follow the coding standards and maintain clean, readable code.


