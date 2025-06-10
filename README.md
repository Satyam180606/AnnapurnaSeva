# Annapurna Seva

**Nourishing Communities, One Meal at a Time**

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Architecture & Data Model](#architecture--data-model)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

Annapurna Seva is an Android application designed to connect food-donating restaurants with NGOs in need. Restaurants post surplus food donations, and NGOs can browse these donations and submit their food requests with details such as quantity, estimated price, and their organizational information. All data is stored and synchronized in real time using Firebase Realtime Database.

## Features

- **User Authentication:**  
  Secure login and signup for both NGOs and restaurants via dedicated screens.
  
- **NGO Module:**  
  - View food donations posted by restaurants.  
  - Submit food donation requests including quantity, estimated price, NGO name, and address.
  
- **Restaurant Module:**  
  - Post food donations with details like name, description, price, and image URL.  
  - View donation requests provided by NGOs.
  
- **Real-Time Data Synchronization:**  
  Powered by Firebase Realtime Database to provide instant updates across devices.

## Architecture & Data Model
root ├─ users │   ├─ ngos │   │   └─ {username} │   │       ├─ password: String │   │       ├─ ngoName: String │   │       └─ location: String │   └─ restaurants │       └─ {username} │           ├─ password: String │           ├─ restaurantName: String │           ├─ fssaiCode: String │           └─ location: String ├─ donationRequests │   └─ {requestId} │       ├─ quantity: String │       ├─ estimatedPrice: String │       ├─ ngoName: String │       └─ ngoAddress: String └─ foodItems └─ {itemId} ├─ name: String ├─ description: String ├─ price: Double ├─ imageUrl: String ├─ restaurantName: String └─ restaurantLocation: String

## Installation & Setup

### Prerequisites

- Android Studio Chipmunk (or later)
- Android SDK (API Level 21 or above)
- A Firebase project with Realtime Database enabled

### Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-org/annapurnaseva.git
   cd annapurnaseva
2. **Open the Project in Android Studio:**
- Select Open an existing Android Studio project and navigate to your cloned repository folder.
3. **Configure Firebase:**
- Log in to the Firebase Console and create a new project.
- Add an Android app with the package name com.example.annapurnaseva.
- Download the google-services.json file and place it in the app/ directory.
4. **Sync Gradle and Run**:
- Click Sync Now in Android Studio and then run the app on your emulator or a physical device.


##Configuration

1.  **Database Rules (development/testing only):**
    {
  "rules": {
    ".read": true,
    ".write": true
  }
}
For production, secure your database with proper read/write rules.

Usage
- Sign Up / Log In:
- Users select their type (NGO or Restaurant) and complete the signup/login process using the provided forms.
- NGO Workflow:
- Browse available food donations.
- Submit food donation requests with details like quantity, estimated price, NGO name, and address.
- Restaurant Workflow:
- Post detailed food donation items.
- Review donation requests submitted by NGOs.
- Real-Time Data:
- All data is updated instantly across devices due to the integration with Firebase.


Project Structure
app/src/main/java/com/example/annapurnaseva/
├─ LoginActivity.java
├─ NGOSignupActivity.java
├─ RestaurantSignupActivity.java
├─ NGOActivity.java
├─ RestaurantActivity.java
├─ RequestDonationActivity.java
├─ DonationRequestsActivity.java
├─ DonateActivity.java
├─ DonationsListActivity.java
└─ models/
    ├─ DonationRequest.java   ← (Alternatively, defined as a public static inner class)
    └─ FoodItem.java

Contributing
Contributions are welcome! To contribute:
- Fork the Repository.
- Create a New Branch:
     git checkout -b feature/YourFeature
- Commit Your Changes:
    git commit -m "Describe your changes here"
- - Push the Branch and Open a Pull Request.
For detailed guidelines, refer to the CONTRIBUTING.md file.
License
This project is licensed under the MIT License.
Contact
For further questions or contributions, please contact Your Name.




