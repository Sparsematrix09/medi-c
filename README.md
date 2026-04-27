# Clinic Management System

This is a Java-based console application designed to streamline clinic operations, focusing on managing caregivers, dependents, appointments, prescriptions, and billing.  
It uses file-based persistence to store data between sessions.

---

## 🚀 Key Features

### 👥 User Management
- Register caregivers
- Link caregivers with dependents (family members)

### 🪪 ID Validation & Privacy
- Built-in validation for NRIC and Passport numbers
- Automatic masking of sensitive IDs in outputs

### 🏥 Clinic Operations
- Manage doctor records
- Book appointments (VIDEO / CLINIC)
- Appointment lifecycle:
  - REQUESTED → CONFIRMED → COMPLETED / CANCELLED

### 💊 Medical & Billing System
- Add prescriptions for completed appointments
- Automated billing:
  - Consultation fee
  - Prescription charges
  - SST (6%)
- Multiple payment methods:
  - Card / E-Wallet
  - Insurance

### 📦 Medicine Fulfilment
- Counter pickup
- Home delivery
- Fulfilment tracking per appointment

### 💾 Data Persistence
- All records stored in `.txt` files
- Human-readable file structure

---

## 🛠️ Technology Stack
- **Language:** Java 8+
- **Storage:** File I/O (Plain Text)
- **Architecture:** Object-Oriented Programming (OOP)

---

## 🧠 OOP Design Justification

This project follows core OOP principles:

### 🔒 Encapsulation
- All fields in classes are private/protected
- Access controlled via getters and setters

### 🧬 Inheritance
- `Patient` is a base class
- `Caregiver` and `Dependent` extend `Patient`

### 🎭 Abstraction
- `Patient` is an abstract class
- Interfaces used:
  - `Payment`
  - `Fulfilment`

### 🔄 Polymorphism
- `Payment.process()` → Card/E-Wallet, Insurance
- `Fulfilment.fulfil()` → CounterPickup, HomeDelivery

### 🧩 Composition
- `Bill` contains:
  - `Appointment`
  - List of `Prescription`

### 👨‍👩‍👧 Multiplicity
- One Caregiver → many Dependents

---

## 🏗️ Getting Started

### 📌 Prerequisites
- Java JDK 8 or higher
- Terminal / Command Prompt / IDE (VS Code recommended)

---

## ▶️ Compilation & Running

### Compile
```bash
javac MediLinkPlus/*.java
run: java MediLinkPlus.Main
```
