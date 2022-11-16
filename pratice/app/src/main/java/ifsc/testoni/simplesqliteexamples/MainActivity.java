package ifsc.testoni.simplesqliteexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = openOrCreateDatabase("db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS students (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
    }

    public void insertNote() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "Thales");
        db.insert("students", null, contentValues);
    }

    public void showStudents() {
        Cursor cursor = db.rawQuery("SELECT id, name FROM students", null);
        cursor.moveToFirst();

        ArrayList<String> names = new ArrayList<>();

        do {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            names.add(name);
        } while (cursor.moveToNext());
    }
}