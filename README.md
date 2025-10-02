# MedFlow
MediFlow is a full-stack web application designed to streamline the operations of a medical center.
It helps staff efficiently manage patients, doctors, appointments, pharmacy, billing, and AI-powered disease prediction.
The system also supports role-based security and digital payments for a modern healthcare experience.

🚀 Key Features

👨‍⚕️ Patient & Doctor Management → Full CRUD operations.

📅 Appointment Booking → Staff can schedule/manage appointments.

💊 Pharmacy Inventory → Manage medicines stock.

📄 Automated Billing → Generate bills automatically for completed appointments.

💳 Online Payment (Razorpay) → Secure digital payments for bills.

🔐 Role-Based Security

ROLE_ADMIN → Full access

ROLE_USER → Read-only access

🤖 AI Disease Predictor → Python ML model predicts possible diseases from symptoms.

📊 Interactive Dashboard → Real-time statistics with Chart.js.

🏗️ Technologies Used
Backend

Java 17

Spring Boot (REST APIs, Security, JPA, Thymeleaf)

MySQL (Database)

Spring Security (Authentication & Authorization)

Frontend

Thymeleaf (Templates)

HTML, CSS, Bootstrap

Chart.js (Dashboard charts)

AI Service

Python 3

Flask (to expose AI model as API)

Scikit-learn (Machine Learning)

Pandas, NumPy (Data handling)

Other Tools

Razorpay API (Payments)

Maven (Build tool)

Lombok (Boilerplate code reduction)
