📚 Library Management System (JDBC + MySQL)

A console-based Library Management System developed using Core Java, JDBC, and MySQL, designed to simulate real-world library operations such as managing books, members, issuing/returning books, and generating reports.
This project follows clean DAO architecture, proper database normalization, and secure credential handling using external configuration files.

🚀 Features
📖 Book Management
Add new books
View all books
Search books by Category
Search books by Author
Low stock alert based on threshold

👤 Member Management
Add new members
View all members with:
Member ID
Name
Email
Phone number

🔄 Issue & Return System
Issue book using Book ID & Member ID
Auto-generate and display Issue ID
Return book using Issue ID
Automatic fine calculation (₹5/day after 14 days)
Tracks issue date, issue time, return date, and return time

📊 Reports
Top 5 most issued books
Low stock alerts

🛠️ Technologies Used
Java (Core + JDBC)
MySQL 8.0
Eclipse IDE
MySQL Connector/J
Git & GitHub


🗂️ Project Structure
LibraryManagementSystem
│
├── src/
│   └── com.library
│       ├── app
│       │   └── LibraryApp.java
│       ├── dao
│       │   ├── BookDAO.java
│       │   ├── MemberDAO.java
│       │   └── IssueDAO.java
│       └── db
│           └── DBConnection.java
│
├── resources/
│   └── db.properties   (ignored by Git)
│
├── .gitignore
├── README.md
