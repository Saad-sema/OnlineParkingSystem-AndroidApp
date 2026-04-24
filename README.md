# 🅿️ Online Parking System – Android App

[![Android](https://img.shields.io/badge/Platform-Android-green?logo=android)](https://developer.android.com)
[![Java](https://img.shields.io/badge/Language-Java-orange?logo=java)](https://www.java.com)
[![PHP](https://img.shields.io/badge/Backend-PHP-777BB4?logo=php)](https://www.php.net)
[![MySQL](https://img.shields.io/badge/Database-MySQL-4479A1?logo=mysql)](https://www.mysql.com)
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
- Register & Login (Email & Password)
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
| Language     | Java                          |
| IDE          | Android Studio                |
| Backend      | PHP (REST APIs)               |
| Database     | MySQL                         |
| Networking   | Volley / Retrofit             |
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
│
├── server/                             # PHP Backend
│   ├── config/
│   │   └── db_connect.php              # MySQL DB connection
│   ├── api/
│   │   ├── login.php
│   │   ├── register.php
│   │   ├── get_slots.php
│   │   ├── book_slot.php
│   │   └── cancel_booking.php
│   └── parking_db.sql                  # MySQL database schema
│
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio **Hedgehog** or later
- Android SDK **API 21+** (Android 5.0 Lollipop and above)
- A local or hosted server with **PHP** and **MySQL** (e.g., XAMPP / WAMP / cPanel)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/saad-seema/olineparkingsystem-androidapp.git
   cd olineparkingsystem-androidapp
   ```

2. **Open in Android Studio**
   - Launch Android Studio → `File` → `Open` → select the project folder

3. **Setup the PHP Backend**
   - Copy the `server/` folder to your web server root (e.g., `htdocs/` for XAMPP)
   - Import the database:
     ```bash
     mysql -u root -p < server/parking_db.sql
     ```
   - Update DB credentials in `server/config/db_connect.php`:
     ```php
     $host = "localhost";
     $user = "root";
     $password = "";
     $database = "parking_db";
     ```

4. **Update API Base URL in Android App**
   - In `utils/Constants.java` (or wherever your base URL is defined), set:
     ```java
     public static final String BASE_URL = "http://YOUR_SERVER_IP/server/api/";
     ```

5. **Build & Run**
   - Connect a device or start an emulator
   - Click **Run ▶** or use:
     ```bash
     ./gradlew assembleDebug
     ```

---

## 🗄️ Database Structure (MySQL)

```sql
-- Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Parking Slots Table
CREATE TABLE parking_slots (
    id INT AUTO_INCREMENT PRIMARY KEY,
    slot_number VARCHAR(10),
    location VARCHAR(150),
    is_available TINYINT(1) DEFAULT 1,
    price_per_hour DECIMAL(10,2),
    vehicle_type VARCHAR(50)
);

-- Bookings Table
CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    slot_id INT,
    start_time DATETIME,
    end_time DATETIME,
    total_amount DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'active',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (slot_id) REFERENCES parking_slots(id)
);
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

- [PHP Documentation](https://www.php.net/docs.php)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Android Developers](https://developer.android.com)
- [Material Design Guidelines](https://material.io/design)

---

> ⭐ If you found this project helpful, please give it a star!
