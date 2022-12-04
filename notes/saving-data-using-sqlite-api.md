## 📑 Summary

- [Complete example class](#complete-example-class)
  - [Exaplanning complete example](#exaplanning-complete-example)
- [Basic operations](#basic-operations)
  - [Inserting data](#inserting-data)
  - [Updating data](#updating-data)
  - [Deleting data](#deleting-data)
  - [Querying data](#querying-data)
- [Resources](#-resources)

## 📍 Complete example class

This is an db class full implmentation of SQLite configurations and operations.

```java
package ifsc.testoni.simplesqliteexamples;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StudentsDB extends SQLiteOpenHelper {
    private static final String TAG = "sql";
    public static final String DB_NAME = "notes.sqlite";
    public static final int DB_VERSION = 1;

    public StudentsDB(Context context) {
        // context, dbname, factory, dbversion
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating students table");
        db.execSQL("create table if not exists students (_id integer primary key autoincrement, name text)");
        Log.d(TAG, "Student table created with successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Execute an sql here if db_version change
    }

    public long save(Student student) {
        long id = student.id;

        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("name", student.name);

            if (id != 0) {
                String _id = String.valueOf(student.id);
                String[] whereArgs = new String[] { _id };
                // update student set values = ... where _id=?
                int count = db.update("student", values, "_id=?", whereArgs);
                return count;
            } else {
                //insert into student values (...)
                id = db.insert("student", "", values);
                return  id;
            }
        } finally {
            db.close();
        }
    }

    public int delete(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from student where _id=?
            int count = db.delete("student", "_id=?", new String[] { String.valueOf(student.id) });
            Log.i(TAG, "Deleted [" + count + "] record");
            return count;
        } finally {
            db.close();
        }
    }

    public List<Student> findAll() {
        SQLiteDatabase db = getWritableDatabase();

        try {
            // select * from student - params: table, columns, selection, selectionArgs, groupBy, having, orderBy, limit
            Cursor cursor = db.query("student", null, null, null, null, null, null, null);
            return toList(cursor);
        } finally {
            db.close();
        }
    }

    public Student findById(long studentId) throws SQLiteException {
        SQLiteDatabase db = getWritableDatabase();

        try {
            Student student = new Student();

            Cursor cursor = db.query("student", null, "_id=?", new String[] { String.valueOf(studentId) }, null, null, null, null);

            if (cursor.moveToFirst()) {
                student.id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                student.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            } else {
                throw new SQLiteException();
            }

            return student;
        }
        finally {
            db.close();
        }
    }

    private List<Student> toList(Cursor cursor) {
        List<Student> students = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                students.add(student);
                student.id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                student.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            } while (cursor.moveToNext());
        }

        return students;
    }
}
```

### Exaplanning complete example

On android, the classes that needs to open or create a database, must inherit from `SQLiteOpenHelper`.

```java
public class StudentDB extends SQLiteOpenHelper {}
```

On constructor is expected the context, database name, a factory and database version.

```java
public StudentsDB(Context context) {
    // context, dbname, factory, dbversion
    super(context, DB_NAME, null, DB_VERSION);
}
```

To use the database, you must call `getWritableDatabase` or `getReadableDatabase`. Both methods returns the object of type `SQLiteDatabase` that represents the database connection. If the database not exits, the `onCreate` method is called to create tables.

```java
@Override
public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table if not exists students (_id integer primary key autoincrement, name text)");
}
```

If the database version passed in the constructor is different of the actual version, the `onUpdate` method is called to execute necessary scripts to add/update tables or columns.

```java
@Override
public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    // Execute an sql here when db version change
}
```

After create basic tempalte, inside class we can create the methods that we want following the basic logic: get object `SQLiteDatabase`, to do something and close connection.

```java
public void doSomething() {
    SQLiteDatabase db = getWritableDatabase();

		try {
        // do something
    } finally {
       db.close(); // close db connection
    }
}
```

The `getWritableDatabase` method will try open database and if not exists, the `onCreate` method will be called to create tables.

## 🎯 Basic operations

### ⬇ Inserting data

To insert data on database it's need create an objet of type `android.content.ContentValues`, that cotains a structued of type key-value, on each key represents the table column name.
After create `ContentValues` object with the values to insert on database, we should call method `insert(tableName, nullColumnHack, contentValues)`.

```java
// Inserting a new student
ContentValues values = new ContentValues();
values.put("name", "Student 1");
db.insert("students", null, values);
```

The above code generates follow sql:

```sql
insert into students(name) values('Student 1');
```

### 🔃 Updating data

To update a record on database we should be call the `update` method from `SQLiteDatabase` object.

```java
long id = 1;

ContentValues values = new ContentValues();
values.put("name", "New name student");

// params: table, values, where, whereArgs
db.put("student", values, "_id=?", id);
```

The put method will generate follow sql:

```sql
update student set name='New name student' where _id=1
```

### 🗑️ Deleting data

To delete a record from database, we should use method `delete(table, where, whereArgs)`.

```java
long id = 1;

db.delete("student", "_id=?", id);
```

Generated SQL:

```sql
delete from "student" where id=1;
```

###  Querying data

To query data, we should be call method `query(...)`, passing as main arguments the table name and the where clauses. The object returned from `query` method is of type `android.database.Cursor`, that will be used to read the values returned from query.

```java
string id = 1;

// params: table, columns, where, whereArgs, groupBy, having, orderBy
Cursor cursor = db.query("student", new String[] { "_id", "name" }, "_id=?", new String[] { id }, null, null, null);

// if found data
if (cursor.getCount > 0) {
    Student student = new Student();
    student.id = cursor.getLong(0);
    student.name = cursor.getLong(1);
}
```

## 🔗 Resources
- [Android docs - Save data using SQLite](https://developer.android.com/training/data-storage/sqlite)
- [Google Android book (chap 18, pg 551)](https://novatec.com.br/livros/google-android-5ed/)