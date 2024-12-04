# 🍽️ Foodie: SUT Food Reservation Client

Welcome to **Foodie**, a client for the Sharif University of Technology (SUT) food reservation system. This app directly interacts with the **Samad** servers, and no external server is involved. **All processes are handled on your phone**, ensuring your data privacy and security.

## 🖥️ Project Description

Foodie simplifies the food reservation process for SUT students, allowing seamless interaction with the Samad servers. As the app is under active development, new features will be added in the future.

### ✨ Features

#### 1. **Login**

-   The first time you log in, your **username** and **password** are securely stored on your phone.
-   As long as the app can authenticate with Samad servers using your credentials, you won't need to log in again.

#### 2. **Food Reservation**

-   Reserve food for the upcoming week by simply selecting the desired meal.
-   **Note**: Based on university policy, double booking may not be allowed. You must cancel an existing reservation to book another for the same time slot.
-   Choose from different reservation locations within the university.

#### 3. **Reservation Information**

-   View all your reservations in a convenient card-list format.
-   Regardless of the reservation location, your bookings are aggregated in one place for easy management.
-   Get the forget code on demand for each food.

#### 4. **Profile Inforamtion**

-   View personal information retrieved directly from the Samad system.
-   **Important**: There is **no intermediary server** between your phone and the Samad servers, ensuring that your personal data remains secure and private.

#### 5. **Automatic Reservation**

-   Select your priorities and just click on a button so the app starts reservation process in real-time.
-   The priority table will update itself on automatic reserves.

#### 6. **Daily Sale**

-   Buy food when a daily sale program is defined by the university.

#### 7. **Reminder Notification**

-   Foodie tries to remind you to not to forget food reservation.

## 🛠️ Project Architecture & Tech Stack

Foodie follows the **Model-View-ViewModel (MVVM)** architecture, a design pattern officially recommended by Google for Android development. This separation of concerns ensures better maintainability, testability, and scalability of the app.

### Frameworks & Libraries

-   **Jetpack Compose**: Used for UI design, leveraging declarative components. Compose simplifies the creation of dynamic and modern UIs.
-   **Retrofit2**: Handles API communication with Samad servers.
-   **Koin**: A lightweight dependency injection framework that facilitates modular development. Koin was chosen over Dagger-Hilt for its simplicity and ease of integration.
-   **Room**: A persistence library that provides an abstraction layer over SQLite to manage the local database efficiently.
-   **Coroutines**: Used for asynchronous programming, ensuring smooth and responsive user interactions.

## 🤝 Contributing

I welcome contributions! If you'd like to contribute, please check out the [CONTRIBUTING.md](contributing.md) for guidelines.

## 📜 License

Copyright (c) 2024 Amir Hossein Ravan Nakhajvani.  
_Foodie_ is licensed under a custom MIT License with additional restrictions. See the [LICENSE](LICENSE) file for details.

## 👨🏻‍💻 About the Author

I’m **Amir Hossein Ravan Nakhjavani**, a passionate CE student. If you have any feedback or questions about this project, feel free to contact me via email. I’m open to discussions on design, implementation, or potential improvements!
