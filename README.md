# CODSOFT Java Internship Tasks

Welcome to the **CODSOFT Java Internship Tasks** repository! This repository includes detailed Java projects completed as part of the CodSoft Java Development internship. Each task reflects core Java concepts, object-oriented programming, data structures, algorithms, and practical application development with clear requirements and modular code. **All tasks are implemented in Java, and tasks involving persistent data use a MySQL database.**

## Table of Contents

- [About the Internship](#about-the-internship)
- [Tech Stack & Requirements](#tech-stack--requirements)
- [Tasks Overview](#tasks-overview)
    - [Task 1: Number Game](#task-1-number-game)
    - [Task 2: Student Grade Calculator](#task-2-student-grade-calculator)
    - [Task 3: ATM Interface](#task-3-atm-interface)
    - [Task 4: Currency Converter](#task-4-currency-converter)
    - [Task 5: Student Management System](#task-5-student-management-system)
- [How to Run](#how-to-run)
- [Contact](#contact)

## About the Internship

This internship, offered by CodSoft, is focused on practical Java skill development through real-world tasks. Completing at least 3 tasks is required for certification. The experience emphasizes leadership, learning, and hands-on coding.

## Tech Stack & Requirements

- **Java** (JDK 8+)
- **MySQL** (for persistent data storage in relevant tasks)
- JDBC Driver for MySQL
- IDE: IntelliJ IDEA / Eclipse / VSCode (or any Java IDE)
- Maven or Gradle (optional, for dependency management)
- Internet connection (for Task 4: Currency Converter API integration)

### External Libraries

- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
- [Gson or Jackson (for JSON parsing, if needed in Currency Converter)]

## Tasks Overview

### Task 1: Number Game

**Objective:**  
- Generate a random number in a specified range.
- Prompt user for guesses; give feedback (too high/too low/correct).
- Limit attempts and allow multiple rounds.
- Track and display user score based on attempts/rounds.

**Key Features:**  
- Command-line interaction.
- Option for replay and scoring.

### Task 2: Student Grade Calculator

**Objective:**  
- Input marks for subjects (out of 100 each).
- Calculate total marks and average percentage.
- Assign and display grade based on percentage.

**Key Features:**  
- Command-line interface.
- Data validation to ensure correct input.

### Task 3: ATM Interface

**Objective:**  
- Console-based ATM simulation.
- Options: Withdraw, Deposit, Check Balance.
- User authentication (optional enhancement).
- Data persisted in a MySQL database (accounts/balances).

**Key Features:**  
- `ATM` and `BankAccount` classes.
- Methods: `withdraw(amount)`, `deposit(amount)`, `checkBalance()`.
- Input validation and informative messages.
- JDBC used for connecting and updating account data.

### Task 4: Currency Converter

**Objective:**  
- User selects base/target currencies.
- Fetch real-time exchange rates using a public API (e.g., ExchangeRate-API, Open Exchange Rates).
- Input amount; output converted value with symbol.

**Key Features:**  
- Live API call (requires internet).
- Error handling for invalid currency/amount input.
- JSON parsing with Gson/Jackson (as needed).

### Task 5: Student Management System

**Objective:**  
- Create and manage student records (name, roll number, grade, etc.).
- Add, remove, update, search, and display students.
- Data stored and retrieved from a MySQL database.

**Key Features:**  
- `Student` and `StudentManagementSystem` classes.
- Console or GUI interface (Swing/JavaFX optional).
- File or database I/O for persistent records (MySQL used here).
- Input validation for all fields.

## How to Run

1. **Clone the repository:**
    ```
    git clone https://github.com/yourusername/CODSOFT.git
    cd CODSOFT
    ```

2. **Database setup (MySQL):**
    - Ensure MySQL Server is installed and running.
    - Import provided SQL schema from `/sql` directory for ATM and Student Management tasks.
    - Update DB connection settings in `src/database/DBConfig.java`.

3. **Build & Run:**
    - Compile with your IDE, or via terminal:
        ```
        javac -cp .;mysql-connector-java.jar src/**/*.java
        java -cp .;mysql-connector-java.jar src/<MainClass>
        ```
    - For Maven/Gradle projects, use `mvn clean package` or `./gradlew build` and run the JAR.

    - For tasks fetching exchange rates, ensure you have an active internet connection and update any required API keys.

## Contact

- **Email:** piyushpate352@gmail.com

### The purpose of this internship is to learn and grow. Approach each task with professional diligence and reach out for help if needed!

> *This repository serves as evidence of Java programming proficiency, familiarity with MySQL integration, and the ability to deliver structured, real-world solutions as part of the CodSoft internship.*

