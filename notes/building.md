## Basic SQLite

The sqlite its a lib part of android-sdk.

1. Creating a database

```java
  SQLiteDatabase db = openOrCreateDatabase("db", MODE_PRIVATE, null);
```

- The SQLiteDatabase is a type defined in `android.database.sqlite`.
- The openOrCreateDatabase method is from activity context.

2. Creating a table

```java
  db.execSQL("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
```

3. Inserting value into table

```java
  ContentValues contentValues = new ContentValues();
  contentValues.put("name", "Thales");
  db.insert("students", null, contentValues);
```

The values passed to the `contentValues.put()` reflects a key value.

4. Querying data

```java
  Cursor cursor = db.rawQuery("SELECT id, name FROM students", null);
  cursor.moveToFirst();

  ArrayList<String> names = new ArrayList<>();

  do {
      String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
      names.add(name);
  } while (cursor.moveToNext());
  
```

The type `Cursor` is imported from `android.database`. The structure of cursor is a matrix and when the `db.rawQuery` finish the query, the pointer of this matrix appoints to the last record, because of this `cursor.moveToFirst` is called.

When line reading is fineshed, `cursor.moveToNext()` is called to appoint the cursor to the next line.