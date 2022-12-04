## 📑 Summary

- [Introduction](#-introduction)
- [Entity](#-entity)
- [DAO - Data access object](#-dao---data-access-object)
- [Room database](#-room-database)
- [Resources](#-resources)

## 📍 Introduction

Here stay my notes realized during my studies about SQLite on Android using Room. This doc contemplate only concepts about Room. If you want to see the complete implementation in a android application, go to the [`pratice/saving-data-using-room`](/pratice/saving-data-using-room/).

## 🔖 Entity

Using ROOM, you must create a class that represents the database table. This class wil anotated with ROOM props to indicate the table name, primary keys, etc. See below ho to create a class that represents the entity:

```java
@Entity(tableName = "word_table")
public class Word {

   @PrimaryKey(autoGenerate = true)
   @NonNull
   @ColumnInfo(name = "word")
   private String mWord;

   public Word(@NonNull String word) {this.mWord = word;}

   public String getWord(){return this.mWord;}
}
```

Too see more: [Defining data using Room entities](https://developer.android.com/training/data-storage/room/defining-data.html)

## 🔍 DAO - Data access object

A [DAO](https://developer.android.com/training/data-storage/room/accessing-data.html) validates SQL at compile-time and associates it with a method. Room use the DAO to create a clean API to store and manage data. In Room DAO is possible uses simple annotations to create and associate a SQL to a method. Example:

```java
@Dao
public interface WordDao {

   // allowing the insert of the same word multiple times by passing a 
   // conflict resolution strategy
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   void insert(Word word);

   @Query("DELETE FROM word_table")
   void deleteAll();

   @Query("SELECT * FROM word_table ORDER BY word ASC")
   List<Word> getAlphabetizedWords();
}
```

To see more: [Acessing data using Room DAOs](https://developer.android.com/training/data-storage/room/accessing-data.html)

## 💾 Room database

According to Android docs, what is a Room database:

- Room is a database layer on top of an SQLite database.
- Room takes care of mundane tasks that you used to handle with an SQLiteOpenHelper.
- Room uses the DAO to issue queries to its database.
- By default, to avoid poor UI performance, Room doesn't allow you to issue queries on the main thread. When Room queries return LiveData, the queries are automatically run asynchronously on a background thread.
- Room provides compile-time checks of SQLite statements.

Implementing the Room database:

```java
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

   public abstract WordDao wordDao();

   private static volatile WordRoomDatabase INSTANCE;
   private static final int NUMBER_OF_THREADS = 4;
   static final ExecutorService databaseWriteExecutor =
        Executors.newFixedThreadPool(NUMBER_OF_THREADS);

   static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
```

To see more: [Room Database](https://developer.android.com/reference/androidx/room/Database)

## 🔗 Resources

- [Guide to app architecture](https://developer.android.com/codelabs/android-room-with-a-view)
- [Codelab Android Room with a View - Java](https://developer.android.com/codelabs/android-room-with-a-view)
- [Save data in a local database using Room](https://developer.android.com/training/data-storage/room)
- [Android Room API reference](https://developer.android.com/codelabs/android-room-with-a-view)
- [Defining data using Room entities](https://developer.android.com/training/data-storage/room/defining-data.html)
- [Acessing data using Room DAOs](https://developer.android.com/training/data-storage/room/defining-data.html)
- [LiveData overview](https://developer.android.com/topic/libraries/architecture/livedata.html)
- [LiveData and Lifecycle](https://www.youtube.com/watch?v=OMcDk2_4LSk&list=PLWz5rJ2EKKc9mxIBd0DRw9gwXuQshgmn2&index=9)
- [Create dynamic lists with RecyclerView](https://developer.android.com/develop/ui/views/layout/recyclerview)
- [Dialogs](https://developer.android.com/develop/ui/views/components/dialogs#ShowingADialog)
- [Search a RecyclerView using SearchView - ROOM Database](https://www.youtube.com/watch?v=rn53Roy-HgE)
