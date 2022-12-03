## 📒 SQLite android notebook

Note from my studies aboute SQLite on android. This is part what i'm learning about develop to mobile devices in my graduation degree.

At first of my studies i was using pure SQLite API to save data on SQLite database, but querying Android documentations i met the Android ROOM library, that provides an abstraction layer over SQLite. In Android docs, ROOM is recommended for more robust app and database. That's why, this repository contains two implementations of SQLite, one using pure SQLite API and other using ROOM.

### 📌 Repository structure

This repository contains two main folders:

- [notes](/notes/): All notes from my studies about SQLite on Android.
- [pratice](/pratice/): Two hands-on applications using SQLite on Android.

### ✍️ Notes structure

- [saving-data-using-room](/notes/saving-data-using-room.md): Notes from learnings about how save data on SQLite database using the Android ROOM library.

- [saving-data-using-sqlite](/notes/saving-data-using-sqlite-api.md): Notes from learnings about how save data on SQLite database using the pure SQLite API.

### 👨‍💻 Pratice structure

- [saving-data-using-room](/pratice/saving-data-using-room/): This pratice is a todo list with CRUD operations and uses the library [Android ROOM](https://developer.android.com/jetpack/androidx/releases/room) to save data on SQLite database. This library provides an abstraction layer over SQLite.

- [saving-data-using-sqlite-api](/pratice/saving-data-using-sqlite-api/): This pratice isn't an usable app, but it's a reference that how configure SQLite database using the pure SQLite API on Android.

### 🔗 Resources

- [About SQLite](https://www.sqlite.org/about.html)
- [Appropriate uses for SQLite](https://www.sqlite.org/whentouse.html)
