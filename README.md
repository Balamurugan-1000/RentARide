# Vehicle Renting App

## 🚀 Project Overview

The **Vehicle Renting App** is a full-stack web application for renting and sharing vehicles. Built with **Angular** for the frontend and **Spring Boot** for the backend, it ensures a smooth user experience.

---

## 🛠️ Tech Stack

### Frontend
- **Framework**: Angular
- **Styling**: Bootstrap

### Backend
- **Framework**: Spring Boot
- **Database**: PostgreSQL

---

## 🌟 Features

### User Features
- **Register/Login**: Secure user authentication.
- **Browse Vehicles**: View available vehicles for rent.
- **Rent a Vehicle**: Rent vehicles approved by their owners.
- **Return Vehicle**: Request to return rented vehicles.

### Owner Features
- **Add Vehicles**: Add and manage vehicles.
- **Approve Requests**: Approve rental and return requests.
- **Toggle Shareable Status**: Mark vehicles as shareable or not.

---

## 💻 Setup Instructions

### Prerequisites
- **Node.js**
- **Java**
- **PostgreSQL**

### Steps to Run

#### Backend
1. Clone the repository:
   ```bash
   git clone https://github.com/Balamurugan-1000/RentARide.git
   cd Rent-A-Ride-API
   ```
2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/vehicle_renting
   spring.datasource.username=<your-username>
   spring.datasource.password=<your-password>
   ```
3. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```

#### Frontend
1. Navigate to the frontend directory:
   ```bash
   cd rent-a-ride-ui
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the Angular development server:
   ```bash
   ng serve
   ```
4. Access the app at `http://localhost:4200/`.

---

## 🤝 Contributing

1. Fork the repository.
2. Create a feature branch.
3. Commit your changes.
4. Open a pull request.

---



## 📧 Contact

For queries, feel free to reach out:
- **GitHub**: [Balamurugan-1000](https://github.com/Balamurugan-1000)

