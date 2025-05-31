**AnnapurnaSeva**
AnnapurnaSeva is a robust Android application designed to connect restaurants and NGOs in a seamless environment. 
The project allows restaurants to post surplus food items and NGOs to view and avail this food for distribution. 
Built with modern architectural concepts and Material Design principles, this project lays a strong foundation for scalable and maintainable applications.
**Table of Contents**
- Overview
- Features
- Architecture & Project Structure
- Database Schema
- Installation & Setup
- Usage
- Dependencies
- Future Enhancements
- License
**Overview**
AnnapurnaSeva bridges the gap between restaurants and NGOs by providing a secure, user-friendly interface.
Restaurants can register, log in, and post details of surplus food. NGOs, after signing up and logging in, can view the list of available food items along with detailed information about the restaurant.
The app uses the Room persistence library to handle local database operations and adheres to modern Material Design guidelines to ensure an attractive and intuitive user interface.
**Features**
- **User Authentication:**
Secure sign-up and login functionality for both Restaurants and NGOs with persistent sessions.
- **Food Posting:**
Restaurants can post food items including details like food name, quantity, expiry date, and associated restaurant information.
- **Food Viewing:**
NGOs can view a curated list of food items posted by restaurants, which includes the restaurant name and location.
- **Responsive Design:**
The application boasts a clean, modern UI with a sleek landing page accompanied by a soothing gradient background and intuitive call-to-actions.
- **Scalable Architecture:**
Written with good separation of concerns and leveraging Room for local data storage, the foundation is set for future enhancements like real-time synchronization.
**Architecture & Project Structure**
The project is organized using a modular and well-documented structure, ensuring that every component is neatly separated into source code, resources, and configuration.
AnnapurnaSeva/
└── app/
    ├── build.gradle                // Module-level Gradle configuration
    ├── src/
    │   └── main/
    │       ├── AndroidManifest.xml // App manifest declaring activities
    │       ├── java/com/example/annapurnaseva/
    │       │    ├── AppDatabase.java         // Room Database instance
    │       │    ├── FoodItem.java              // Food Item entity
    │       │    ├── FoodItemDao.java           // DAO for food items
    │       │    ├── Restaurant.java            // Restaurant entity
    │       │    ├── RestaurantDao.java         // DAO for restaurants
    │       │    ├── NGO.java                   // NGO entity
    │       │    ├── NGODao.java                // DAO for NGOs
    │       │    ├── LoginActivity.java         // Login screen
    │       │    ├── RestaurantSignupActivity.java  // Restaurant sign-up screen
    │       │    ├── NGOSignupActivity.java     // NGO sign-up screen
    │       │    ├── RestaurantActivity.java    // Restaurant main function (post food)
    │       │    └── NGOActivity.java           // NGO main function (view food)
    │       └── res/
    │           ├── layout/
    │           │    ├── activity_login.xml
    │           │    ├── activity_restaurant.xml
    │           │    ├── activity_restaurant_signup.xml
    │           │    ├── activity_ngo_signup.xml
    │           │    └── activity_ngo.xml
    │           ├── drawable/
    │           │    └── gradient_background.xml    // Background drawable
    │           └── values/
    │                └── styles.xml               // App theme and colors



**Database Schema**
The application uses the Room persistence library to map Java classes to SQLite tables. The primary entities are:
- Restaurants:
Stores restaurant details such as name, FSSAI code, location, username, and password.
- NGOs:
Contains NGO information including name, registration number, office address, username, and password.
- Food Items:
Represents posted food items with fields like name, quantity, expiry date, and a foreign key restaurantId linking to the Restaurants table.
Relationship:
Each food item is associated with a restaurant, ensuring that the posted food details can be traced back to the source.

**Installation & Setup**
- Clone the Repository:
git clone https://github.com/your-username/AnnapurnaSeva.git
cd AnnapurnaSeva
- Open in Android Studio:
Open Android Studio and select "Open an existing Android Studio project." Navigate to the cloned directory.
- Gradle Sync:
Let Android Studio sync and download the necessary dependencies as specified in the build.gradle files.
- Run the App:
Use an emulator or connect your Android device to run the app.

**Usage**
- User Registration:
Choose your user type (Restaurant or NGO) and complete the sign-up process.
- Login:
Use your registered credentials to log in.
- Restaurants:
Post food items by entering details through the RestaurantActivity.
- NGOs:
View the list of available food items along with restaurant details in the NGOActivity.
- Persistent Sessions:
Once logged in, sessions persist until you explicitly sign out from the app.

**Dependencies**
- Room: For database operations and ORM.
- Material Components: For a consistent and visually appealing user interface.
- AndroidX Libraries: For enhanced compatibility and performance.
- Gradle: For building and managing the project.

**Future Enhancements**
- Real-Time Data Synchronization:
Integrate cloud-based solutions to keep the database updated in real time.
- Enhanced Error Handling:
Improve validation and feedback mechanisms for better user experience.
- Advanced Features:
Consider incorporating features like push notifications, detailed analytics, and social sharing.

**License**
This project is licensed under the MIT License. See the LICENSE file for details.

