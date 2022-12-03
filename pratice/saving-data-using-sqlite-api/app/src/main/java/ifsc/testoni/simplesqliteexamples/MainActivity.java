package ifsc.testoni.simplesqliteexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    StudentsDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.db = new StudentsDB(this);
    }

    public void saveStudent() {
        Student student = new Student();
        student.name = "Thales";
        db.save(student);
    }

    public void showStudents() {
        db.findAll();
    }
}