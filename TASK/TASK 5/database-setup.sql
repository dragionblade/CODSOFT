-- Create database
CREATE DATABASE studentdb;

-- Select the database
USE studentdb;

-- Create students table
CREATE TABLE students (
    roll_number VARCHAR(20) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    grade VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Insert sample data for testing (optional)
INSERT INTO students (roll_number, name, grade, email) VALUES
('R001', 'Alice Johnson', 'A', 'alice@example.com'),
('R002', 'Bob Smith', 'B+', 'bob@example.com'),
('R003', 'Carol Lee', 'A-', 'carol@example.com');
