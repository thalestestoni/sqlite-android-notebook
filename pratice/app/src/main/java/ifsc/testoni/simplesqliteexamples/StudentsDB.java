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
