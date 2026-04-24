# 🅿️ Online Parking System – Android App

[![Android](https://img.shields.io/badge/Platform-Android-green?logo=android)](https://developer.android.com)
[![Java](https://img.shields.io/badge/Language-Java-orange?logo=java)](https://www.java.com)
[![Firebase](https://img.shields.io/badge/Backend-Firebase-yellow?logo=firebase)](https://firebase.google.com)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A smart, user-friendly **Online Parking System** Android application that allows users to search, book, and manage parking slots in real time. Designed to reduce parking hassle in malls, colleges, airports, and urban areas.

---

## 📱 Screenshots

> _Add your app screenshots here_

| Home Screen | Search Parking | Booking Confirmation |
|-------------|---------------|----------------------|
| ![home]()   | ![search]()   | ![booking]()         |

---

## ✨ Features

### 👤 User
- Register & Login (Email / Google Sign-In)
- Search available parking slots by location
- View parking slot details (location, price, availability)
- Book a parking slot in real time
- View active & past bookings
- Cancel or modify bookings
- Receive booking confirmation notifications
- Profile management

### 🛠️ Admin
- Add / update / remove parking locations and slots
- View all bookings and manage status
- Dashboard with booking statistics
- Manage user accounts

---

## 🏗️ Tech Stack

| Layer        | Technology                     |
|--------------|-------------------------------|
| Language     | Java / Kotlin                 |
| IDE          | Android Studio                |
| Backend      | Firebase (Firestore / Realtime DB) |
| Auth         | Firebase Authentication       |
| Notifications| Firebase Cloud Messaging (FCM)|
| Maps         | Google Maps API               |
| UI           | XML Layouts, Material Design  |
| Build Tool   | Gradle                        |

---

## 📂 Project Structure

```
olineparkingsystem-androidapp/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/yourpackage/parkingsystem/
│   │   │   │   ├── activities/         # All Activity classes
│   │   │   │   ├── adapters/           # RecyclerView Adapters
│   │   │   │   ├── models/             # Data models (User, Slot, Booking)
│   │   │   │   ├── fragments/          # UI Fragments
│   │   │   │   └── utils/              # Helper & Utility classes
│   │   │   ├── res/
│   │   │   │   ├── layout/             # XML layout files
│   │   │   │   ├── drawable/           # Icons & images
│   │   │   │   └── values/             # Colors, strings, styles
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── google-services.json                # Firebase config (not committed)
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio **Hedgehog** or later
- Android SDK **API 21+** (Android 5.0 Lollipop and above)
- A Firebase project set up at [console.firebase.google.com](https://console.firebase.google.com)
- Google Maps API Key

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/saad-seema/olineparkingsystem-androidapp.git
   cd olineparkingsystem-androidapp
   ```

2. **Open in Android Studio**
   - Launch Android Studio → `File` → `Open` → select the project folder

3. **Configure Firebase**
   - Create a Firebase project and add an Android app
   - Download `google-services.json` and place it in the `/app` directory
   - Enable **Authentication**, **Firestore / Realtime Database**, and **Cloud Messaging**

4. **Add Google Maps API Key**
   - In `AndroidManifest.xml`, add:
     ```xml
     <meta-data
         android:name="com.google.android.geo.API_KEY"
         android:value="YOUR_GOOGLE_MAPS_API_KEY"/>
     ```

5. **Build & Run**
   - Connect a device or start an emulator
   - Click **Run ▶** or use:
     ```bash
     ./gradlew assembleDebug
     ```

---

## 🔑 Firebase Configuration

Ensure the following Firebase services are enabled:

| Service                     | Purpose                          |
|-----------------------------|----------------------------------|
| Firebase Authentication     | User login & registration        |
| Cloud Firestore / Realtime DB | Parking slot & booking data   |
| Firebase Storage            | Profile pictures (optional)      |
| Firebase Cloud Messaging    | Push notifications for bookings  |

---

## 📊 Database Structure (Firestore Example)

```
/users/{userId}
    - name, email, phone, profilePicUrl

/parkingLocations/{locationId}
    - name, address, latitude, longitude, totalSlots, pricePerHour

/parkingSlots/{slotId}
    - locationId, slotNumber, isAvailable, vehicleType

/bookings/{bookingId}
    - userId, slotId, locationId, startTime, endTime, status, totalAmount
```

---

## 🤝 Contributing

Contributions are welcome! Follow these steps:

1. Fork the project
2. Create a new branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m "Add: your feature description"`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a **Pull Request**

Please make sure your code follows the existing code style and all tests pass.

---

## 🐛 Known Issues / TODO

- [ ] Payment gateway integration (Razorpay / Stripe)
- [ ] Real-time slot availability via IoT sensors
- [ ] Dark mode support
- [ ] Multi-language support (Hindi, Gujarati)
- [ ] Booking QR code generation

---

## 📄 License

This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Authors

**Saad & Seema**  
GitHub: [@saad-seema](https://github.com/saad-seema)

---

## 🙏 Acknowledgements

- [Firebase Documentation](https://firebase.google.com/docs)
- [Google Maps Platform](https://developers.google.com/maps)
- [Android Developers](https://developer.android.com)
- [Material Design Guidelines](https://material.io/design)

---

> ⭐ If you found this project helpful, please give it a star!

